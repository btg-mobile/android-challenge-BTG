package com.uchoa.btg.movie.ui.movielist

import com.uchoa.btg.movie.dao.sqlite.FavoriteDao
import com.uchoa.btg.movie.models.Movie

interface MoviesContract {

    interface View {
        fun showError()
        fun showEmptyView()
        fun showMovies(movies: MutableList<Movie>)
    }

    interface Presenter {
        fun getMovies()
        fun getFavoriteMovies()
        fun getDatabase(): FavoriteDao
        fun isLoadingMovie(): Boolean
        fun updateFavoriteStatus(movie: Movie)
        fun resetCurrentPage()
        fun onDestroy()
    }
}