package br.com.questv.themoviebtg.movies.ui

import br.com.questv.themoviebtg.movies.behavior.GenresAdapter

interface MoviesView {
    fun initRecyclerView(genresAdapter: GenresAdapter)
    fun showProgress()
    fun hideProgress()
}