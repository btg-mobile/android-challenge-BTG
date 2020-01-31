package br.com.questv.themoviebtg.movies.behavior

import br.com.questv.themoviebtg.movies.model.GenreModel
import br.com.questv.themoviebtg.movies.ui.MoviesView
import java.lang.Exception

class MoviesPresenter(var movieView: MoviesView) : MoviesInteractor.GenreFetchListener {

    private val moviesInteractor = MoviesInteractor()

    fun fetchAllGenres() {
        this.movieView.showProgress()
        this.moviesInteractor.fetchAllGenres(this)
    }

    override fun onGenreFetchedSuccess(genreModelList: List<GenreModel>) {
        val adapter = GenresAdapter(genreModelList)
        this.movieView.initRecyclerView(adapter)
        this.movieView.hideProgress()
    }

    override fun onGenreFetchedFail(exception: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}