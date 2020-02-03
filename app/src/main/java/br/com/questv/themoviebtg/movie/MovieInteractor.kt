package br.com.questv.themoviebtg.movie

class MovieInteractor {

    interface FetchMovieListener {
        fun onFetchMovieSuccess()
        fun onFetchMovieFail()
    }


    fun fetchMovie(movieId: String, listener: FetchMovieListener) {

        listener.onFetchMovieSuccess()


    }
}