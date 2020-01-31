package br.com.questv.themoviebtg.genres.ui

import br.com.questv.themoviebtg.genres.behavior.GenresAdapter

interface GenresView {
    fun initRecyclerView(genresAdapter: GenresAdapter)
    fun showProgress()
    fun hideProgress()
}