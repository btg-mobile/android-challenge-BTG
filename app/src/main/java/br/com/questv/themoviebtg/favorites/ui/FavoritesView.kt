package br.com.questv.themoviebtg.favorites.ui

import br.com.questv.themoviebtg.favorites.recycler.FavoritesAdapter

interface FavoritesView {
    fun showProgress()
    fun hideProgress()
    fun initRecyclerView(favoritesAdapter: FavoritesAdapter)
}