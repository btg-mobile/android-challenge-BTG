package com.uchoa.btg.movie.ui.moviedetails

import com.uchoa.btg.movie.models.Genre
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.models.Trailer

interface MovieDetailContract {

    interface View {
        fun showError()
        fun showMovieDetail(movie: Movie)
        fun showTrailers(trailers: List<Trailer>)
    }

    interface Presenter {
        fun getMovie(movieId : Int)
        fun getGenres(movie: Movie)
        fun getFormattedGenres(genres: List<Genre>): String
        fun isFavoriteMovie(movieId: Int): Boolean
        fun deleteFavoriteMovie(movieId: Int)
        fun saveFavoriteMovie(movie: Movie)
        fun onDestroy()
    }
}