package br.com.questv.themoviebtg.movies.behavior

import br.com.questv.themoviebtg.movies.model.MovieModel
import java.lang.Exception

class MoviesPresenter(private val moviesView: MoviesView) :
    MoviesInteractor.FetchMoviesByGenreListener {
    private val moviesInteractor = MoviesInteractor()

    fun fetchMoviesByGenre(genre: String) {
        this.moviesView.showProgress()
        this.moviesInteractor.fetchMoviesByGenre(genre, this)

    }

    override fun onMovieByGenreFetchedSuccess(movieModelList: List<MovieModel>) {
        this.moviesView.initRecyclerView(movieModelList)
        this.moviesView.hideProgress()

    }

    override fun onMovieByGenreFetchedFail(exception: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}