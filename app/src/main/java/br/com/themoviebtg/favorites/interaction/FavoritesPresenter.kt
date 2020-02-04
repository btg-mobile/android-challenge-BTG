package br.com.themoviebtg.favorites.interaction

import br.com.themoviebtg.favorites.recycler.FavoritesAdapter
import br.com.themoviebtg.favorites.ui.FavoritesView
import br.com.themoviebtg.movies.model.MovieModel

class FavoritesPresenter(private val favoritesView: FavoritesView) :
    FavoritesInteractor.FavoritesFetchListener {
    private val favoritesInteractor =
        FavoritesInteractor()

    fun fetchFavorites() {
        this.favoritesView.showProgress()
        this.favoritesInteractor.fetchFavorites(this)
    }


    override fun onFavoritesFetchSuccess(favoritesModelList: List<MovieModel>) {
        this.favoritesView.initGridView(
            FavoritesAdapter(
                favoritesModelList
            )
        )
        this.favoritesView.hideProgress()
    }

    override fun onFavoritesFetchFail(exception: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}