package br.com.questv.themoviebtg.movies.behavior

import br.com.questv.themoviebtg.movies.model.MovieModel

interface MoviesView {
    fun showProgress()
    fun hideProgress()
    fun initRecyclerView(movieModelList: List<MovieModel>)
}