package br.com.themoviebtg.search

import br.com.themoviebtg.movies.behavior.MoviesAdapter

interface SearchResultView {
    fun initGridView(moviesAdapter: MoviesAdapter)
    fun showProgress()
    fun hideProgress()
}