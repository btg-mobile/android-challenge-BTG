package br.com.themoviebtg.genres.ui

import br.com.themoviebtg.genres.behavior.GenresAdapter

interface GenresView {
    fun initRecyclerView(genresAdapter: GenresAdapter)
    fun showProgress()
    fun hideProgress()
}