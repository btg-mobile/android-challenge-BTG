package br.com.themoviebtg.favorites.ui

import br.com.themoviebtg.favorites.grid.FavoritesAdapter

interface FavoritesView {
    fun showProgress()
    fun hideProgress()
    fun initGridView(favoritesAdapter: FavoritesAdapter)
}