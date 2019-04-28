package com.example.desafiobtg.usecases.movielist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import com.example.desafiobtg.data.Repository
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.utils.ListUtils.getUncommonItems
import javax.inject.Inject

class MovieListPresenter @Inject constructor(private val mRepository: Repository): MovieListContract.Presenter {

    private var mMovieListView: MovieListContract.View? = null

    private var mMovieList = arrayListOf<Movie>()
    private var mFavoriteMovieIds = listOf<String>()

    private var mFavoriteMoviesLiveData: LiveData<List<String>>? = null

    override fun takeView(view: MovieListContract.View) {
        mMovieListView = view
    }

    override fun dropView() {
        mMovieListView = null
    }

    override fun bindMovieHolder(movieHolder: MovieListContract.MovieHolder?, position: Int) {
        movieHolder?.setMoviePoster("https://image.tmdb.org/t/p/w185${mMovieList[position].posterUrl}")
        mMovieList[position].title?.let { title -> movieHolder?.setMovieTitle(title) }
        mMovieList[position].releaseDate?.let { year -> movieHolder?.setMovieYear(year) }
        movieHolder?.setMovieFavorited(mFavoriteMovieIds.contains(mMovieList[position].id))
    }

    override fun setMovieFavorite(position: Int, favorite: Boolean) {
        val id = mMovieList[position].id
        if (favorite) {
            mRepository.addFavoriteMovie(id)
        } else {
            mRepository.removeFavoriteMovie(id)
        }
    }

    override fun onViewCreated(fragment: Fragment) {
        mFavoriteMoviesLiveData = mRepository.getFavoriteIds()
        mFavoriteMoviesLiveData?.observe(fragment, Observer { idList ->
            idList?.let { ids ->
                val uncommonItems = getUncommonItems(mFavoriteMovieIds, ids)
                uncommonItems.forEach { idToNotify ->
                    val movieToUpdate = mMovieList.find { it.id == idToNotify }
                    val indexToUpdate = mMovieList.indexOf(movieToUpdate)
                    mMovieListView?.notifyFavoriteChanged(indexToUpdate)
                }

                mFavoriteMovieIds = ids
            }
        })

        mRepository.getMovieList(1, { response ->
            response.let {
                mMovieList = ArrayList(it.movieList)

            }
            mMovieListView?.notifyDatasetChanged()
        }, {

        })

    }

    override fun bindFavoriteIcon(movieHolder: MovieListContract.MovieHolder?, position: Int) {
        movieHolder?.setMovieFavorited(mFavoriteMovieIds.contains(mMovieList[position].id))
    }

    override fun getMovieCount() = mMovieList.size

}