package com.uchoa.btg.movie.ui.moviedetails

import android.content.Context
import android.text.TextUtils
import com.uchoa.btg.movie.callbacks.OnGetGenresCallback
import com.uchoa.btg.movie.callbacks.OnGetMovieCallback
import com.uchoa.btg.movie.callbacks.OnGetTrailersCallback
import com.uchoa.btg.movie.dao.sqlite.FavoriteDao
import com.uchoa.btg.movie.models.Genre
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.models.Trailer
import com.uchoa.btg.movie.network.MoviesRepository


class MovieDetailPresenter(var context: Context, var view: MovieDetailContract.View?) : MovieDetailContract.Presenter {

    private var dataBase: FavoriteDao = FavoriteDao(context)
    private var moviesRepository: MoviesRepository? = null

    init {
        moviesRepository = MoviesRepository.instance
    }

    override fun getMovie(movieId: Int) {
        moviesRepository?.getMovie(movieId, object : OnGetMovieCallback {
            override fun onSuccess(movie: Movie) {
                getGenres(movie)
            }

            override fun onError() {
                view?.showError()
            }
        })
    }

    override fun getGenres(movie: Movie) {
        moviesRepository?.getGenres(object : OnGetGenresCallback {
            override fun onSuccess(genres: List<Genre>) {
                if (movie.genres != null) {
                    val movieGenres: MutableList<String> = mutableListOf()
                    for (genre in movie.genres!!) {
                        movieGenres.add(genre.name!!)
                    }
                    view?.showMovieDetail(movie)
                    getTrailers(movie)
                }
            }

            override fun onError() {
                view?.showError()
            }
        })
    }

    private fun getTrailers(movie: Movie) {
        moviesRepository?.getTrailers(movie.id, object : OnGetTrailersCallback {
            override fun onSuccess(trailers: List<Trailer>) {
                if (trailers.isNotEmpty()) {
                    view?.showTrailers(trailers)
                }
            }

            override fun onError() {
                // Do nothing
            }
        })
    }

    override fun getFormattedGenres(genres: List<Genre>): String {
        val movieGenres: MutableList<String> = mutableListOf()

        for (genre in genres) {
            movieGenres.add(genre.name!!)
        }

        return TextUtils.join(", ", movieGenres)
    }

    override fun isFavoriteMovie(movieId: Int): Boolean {
        return dataBase.get(movieId).size > 0
    }

    override fun deleteFavoriteMovie(movieId: Int) {
        dataBase.delete(movieId)
    }

    override fun saveFavoriteMovie(movie: Movie) {
        dataBase.insert(movie)
    }

    override fun onDestroy() {
        view = null
        dataBase.onDestroy()
    }
}