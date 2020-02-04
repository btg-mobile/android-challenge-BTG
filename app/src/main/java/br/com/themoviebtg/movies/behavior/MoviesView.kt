package br.com.themoviebtg.movies.behavior

interface MoviesView {
    fun showProgress()
    fun hideProgress()
    fun initGridView(moviesAdapter: MoviesAdapter)
    fun showErrorMessage(errorMessage: String)
}