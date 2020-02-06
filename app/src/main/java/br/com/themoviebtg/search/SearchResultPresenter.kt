package br.com.themoviebtg.search

import br.com.themoviebtg.movies.behavior.MoviesAdapter
import br.com.themoviebtg.movies.model.MovieModel

class SearchResultPresenter(private val searchResultView: SearchResultView) :
    SearchResultInteractor.OnMovieFetchedListener {
    private val interactor = SearchResultInteractor()

    fun fetchMoviesByQuery(query: String) {
        this.searchResultView.showProgress()
        this.interactor.fetchMoviesByQuery(query, this)
    }

    override fun onFetchedByQuerySuccess(movieModelList: List<MovieModel>) {
        this.searchResultView.initGridView(MoviesAdapter(movieModelList))
        this.searchResultView.hideProgress()
    }

    override fun onFetchedByQueryFail(errorMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}