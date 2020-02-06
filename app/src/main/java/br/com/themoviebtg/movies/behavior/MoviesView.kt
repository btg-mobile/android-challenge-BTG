package br.com.themoviebtg.movies.behavior

import br.com.themoviebtg.movies.model.MovieModel

interface MoviesView {
    fun showProgress()
    fun hideProgress()
    fun initGridView(moviesAdapter: MoviesAdapter)
    fun showErrorMessage(errorMessage: String)
    fun navigateToMovieDetails(movieModel: MovieModel)
}