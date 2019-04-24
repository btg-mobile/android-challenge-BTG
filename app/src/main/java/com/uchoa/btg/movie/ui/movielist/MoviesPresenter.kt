package com.uchoa.btg.movie.ui.movielist

import android.content.Context
import com.uchoa.btg.movie.callbacks.OnGetMoviesCallback
import com.uchoa.btg.movie.dao.sqlite.FavoriteDao
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.network.MoviesRepository

class MoviesPresenter(
    context: Context,
    var view: MoviesContract.View?,
    private var moviesRepository: MoviesRepository = MoviesRepository.instance,
    private var dataBase: FavoriteDao = FavoriteDao(context)
) : MoviesContract.Presenter {

    private var loadingMovie: Boolean = false
    private var currentPage = 0

    override fun getMovies() {
        loadingMovie = true
        moviesRepository.getMovies(++currentPage, object : OnGetMoviesCallback {
            override fun onSuccess(page: Int, movies: MutableList<Movie>) {
                currentPage = page
                view?.showMovies(movies)
                for (movie in movies) {
                    if (dataBase.get(movie.id).size > 0) {
                        movie.favorite = true
                    }
                }
                loadingMovie = false
            }

            override fun onError() {
                view?.showError()
            }
        })
    }

    override fun getFavoriteMovies() {
        val movies = dataBase.getAll()

        if (movies.size > 0) {
            view?.showMovies(movies)
        } else {
            view?.showEmptyView()
        }
    }

    override fun isLoadingMovie(): Boolean {
        return loadingMovie
    }

    override fun getDatabase(): FavoriteDao {
        return dataBase
    }

    override fun updateFavoriteStatus(movie: Movie){
        if (movie.favorite) {
            dataBase.delete(movie.id)
        } else {
            dataBase.insert(movie)
        }
    }

    override fun resetCurrentPage() {
        currentPage = 0
    }

    override fun onDestroy() {
        view = null
        dataBase.onDestroy()
    }
}