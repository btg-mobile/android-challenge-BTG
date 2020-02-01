package br.com.questv.themoviebtg.movie

class MoviePresenter(private val movieView: MovieView) : MovieInteractor.FetchMovieListener{

    private val interactor = MovieInteractor()

    fun fetchMovieById(movieId: String) {
        this.interactor.fetchMovieById(movieId, this)

    }

    override fun onFetchMovieSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFetchMovieFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}