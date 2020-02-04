package br.com.themoviebtg.movies.behavior

import br.com.themoviebtg.movies.model.MovieModel
import java.lang.Exception

class MoviesInteractor {
    interface FetchMoviesByGenreListener {
        fun onMovieByGenreFetchedSuccess(movieModelList: List<MovieModel>)
        fun onMovieByGenreFetchedFail(exception: Exception)
    }

    fun fetchMoviesByGenre(genre: String, listener: FetchMoviesByGenreListener) {
        listener.onMovieByGenreFetchedSuccess(
            listOf(
                MovieModel("1", "Movie1"),
                MovieModel("2", "Movie2"),
                MovieModel("3", "Movie3")
            )
        )

    }
}