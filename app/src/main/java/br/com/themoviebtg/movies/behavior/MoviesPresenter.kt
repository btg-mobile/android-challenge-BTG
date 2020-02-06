package br.com.themoviebtg.movies.behavior

import br.com.themoviebtg.movies.model.MovieModel

class MoviesPresenter(private val moviesView: MoviesView) :
    MoviesInteractor.FetchMoviesByGenreListener {
    private val moviesInteractor =
        MoviesInteractor()

    fun fetchPopularMovies() {
        this.moviesView.showProgress()
        this.moviesInteractor.fetchPopularMovies( this)

    }

    override fun onMovieByGenreFetchedSuccess(movieModelList: List<MovieModel>) {
        this.moviesView.initGridView(MoviesAdapter(movieModelList))
        this.moviesView.hideProgress()

    }

    override fun onMovieByGenreFetchedFail(errorMessage: String) {
        moviesView.showErrorMessage(errorMessage)
    }
}