package br.com.themoviebtg.genres.behavior

import br.com.themoviebtg.genres.model.GenreModel

class GenresInteractor {
    interface GenreFetchListener {
        fun onGenreFetchedSuccess(genreModelList: List<GenreModel>)
        fun onGenreFetchedFail(exception: Exception)
    }


    fun fetchAllGenres(listener: GenreFetchListener) {
        listener.onGenreFetchedSuccess(
            listOf(
                GenreModel("1", "Action"),
                GenreModel("2", "Drama"),
                GenreModel("3", "Horror")
            )
        )

    }
}