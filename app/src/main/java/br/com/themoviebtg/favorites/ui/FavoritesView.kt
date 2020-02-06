package br.com.themoviebtg.favorites.ui

import br.com.themoviebtg.movies.behavior.MoviesAdapter

interface FavoritesView {
    fun showProgress()
    fun hideProgress()
    fun initGridView(moviesAdapter: MoviesAdapter)
}