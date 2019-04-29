package com.example.desafiobtg.usecases.moviedetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.desafiobtg.data.Repository
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.utils.DateUtils
import javax.inject.Inject

class MovieDetailsPresenter @Inject constructor(private val mRepository: Repository): MovieDetailsContract.Presenter {

    private var mMovieDetailsView : MovieDetailsContract.View? = null

    private var mFavoriteIdsLiveData: LiveData<List<String>>? = null

    private var movie: Movie? = null

    override fun takeView(view: MovieDetailsContract.View) {
        mMovieDetailsView = view
    }

    override fun dropView() {
        mMovieDetailsView = null
    }

    override fun takeArguments(arguments: Bundle?, fragment: Fragment) {
        arguments?.let { args ->
            val movieId = args.getString(MovieDetailsFragment.MOVIE_ID_KEY)
            mRepository.getMovieById(movieId, success = { movie ->
                this.movie = movie
                mMovieDetailsView?.setMovieBackdrop("https://image.tmdb.org/t/p/w1280${movie?.backdropUrl}")
                mMovieDetailsView?.setMoviePoster("https://image.tmdb.org/t/p/w300${movie?.posterUrl}")
                movie?.title?.let { title -> mMovieDetailsView?.setMovieTitle(title) }
                val year = DateUtils.getYearForDate(movie?.releaseDate)
                year?.let { mMovieDetailsView?.setMovieYear(it) }
                mMovieDetailsView?.setOverviewText(movie?.overview)
                movie?.genreIds?.let {
                    mRepository.getGenreForIds(it, success = { genreList ->
                        mMovieDetailsView?.showGenreList(genreList)
                    })
                }
            })
        }

        loadFavoriteIds(fragment)
    }

    private fun loadFavoriteIds(fragment: Fragment) {
        mFavoriteIdsLiveData = mRepository.getFavoriteIds()
        mFavoriteIdsLiveData?.observe(fragment, Observer { idList ->
            idList?.let { ids ->
                mMovieDetailsView?.setMovieFavorite(ids.contains(movie?.id))
            }
        })
    }

    override fun setMovieFavorite(isFavorite: Boolean) {
        movie?.id?.let { id ->
            if (isFavorite) {
                mRepository.addFavoriteMovie(id)
            } else {
                mRepository.removeFavoriteMovie(id)
            }
        }
    }
}