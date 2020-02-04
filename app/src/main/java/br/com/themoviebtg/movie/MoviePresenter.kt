package br.com.themoviebtg.movie

import br.com.themoviebtg.movies.model.MovieModel

class MoviePresenter(private val movieView: MovieView) :
    MovieInteractor.FetchMovieListener {

    private val interactor = MovieInteractor()
    private val theMovieDbImagesUrl = "http://image.tmdb.org/t/p/w300/"


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

    fun initView(movieModel: MovieModel) {
        this.movieView.attachMovieOverview(movieModel.overview)
        this.movieView.attachMovieTitle(movieModel.title)
        this.movieView.attachMovieBackground("${theMovieDbImagesUrl}${movieModel.backdrop_path}")

    }


}