package br.com.questv.themoviebtg.movie

class MovieInteractor {

    interface FetchMovieListener {
        fun onFetchMovieSuccess()
        fun onFetchMovieFail()
    }


    fun fetchMovieById(movieId: String, listener: FetchMovieListener) {





    }
}