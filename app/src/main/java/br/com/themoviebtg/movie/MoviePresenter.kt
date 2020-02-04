package br.com.themoviebtg.movie

class MoviePresenter(private val movieView: MovieView) :
    MovieInteractor.FetchMovieListener {

    private val interactor = MovieInteractor()

    fun fetchMovie(movieId: String) {
        this.movieView.showProgress()
        this.interactor.fetchMovie(movieId, this)

    }

    override fun onFetchMovieSuccess() {
        this.movieView.hideProgress()
    }

    override fun onFetchMovieFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}