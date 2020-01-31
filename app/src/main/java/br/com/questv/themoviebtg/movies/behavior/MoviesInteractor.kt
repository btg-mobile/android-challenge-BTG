package br.com.questv.themoviebtg.movies.behavior

import br.com.questv.themoviebtg.movies.model.GenreModel
import java.lang.Exception

class MoviesInteractor {
    interface GenreFetchListener {
        fun onGenreFetchedSuccess(genreModelList: List<GenreModel>)
        fun onGenreFetchedFail(exception: Exception)
    }


    fun fetchAllGenres(listener: GenreFetchListener) {
        listener.onGenreFetchedSuccess(listOf(
            GenreModel("1", "Action"),
            GenreModel("2", "Drama"),
            GenreModel("3", "Horror")
            ))

    }
}