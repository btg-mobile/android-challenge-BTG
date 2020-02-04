package br.com.themoviebtg.favorites.interaction

import br.com.themoviebtg.movies.model.MovieModel

class FavoritesInteractor {
    interface FavoritesFetchListener {
        fun onFavoritesFetchSuccess(favoritesModelList: List<MovieModel>)
        fun onFavoritesFetchFail(exception: Exception)
    }



    fun fetchFavorites(listener: FavoritesFetchListener) {
        val favorites = mutableListOf<MovieModel>()

        listener.onFavoritesFetchSuccess(favorites)
    }
}