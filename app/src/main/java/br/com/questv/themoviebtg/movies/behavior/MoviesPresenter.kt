package br.com.questv.themoviebtg.movies.behavior

import br.com.questv.themoviebtg.movies.ui.MoviesView

class MoviesPresenter(var movieView: MoviesView?) {
    private val moviesInteractor = MoviesInteractor()
    private val moviesGenres = MoviesAdapter()
}