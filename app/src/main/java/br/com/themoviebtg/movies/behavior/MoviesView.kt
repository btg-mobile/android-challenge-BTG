package br.com.themoviebtg.movies.behavior

import br.com.themoviebtg.movies.model.MovieModel

interface MoviesView {
    fun showProgress()
    fun hideProgress()
    fun initRecyclerView(movieModelList: List<MovieModel>)
}