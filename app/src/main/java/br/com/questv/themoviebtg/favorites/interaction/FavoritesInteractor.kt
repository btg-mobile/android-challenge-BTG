package br.com.questv.themoviebtg.favorites.interaction

import br.com.questv.themoviebtg.movies.model.MovieModel

class FavoritesInteractor {
    interface FavoritesFetchListener {
        fun onFavoritesFetchSuccess(favoritesModelList: List<MovieModel>)
        fun onFavoritesFetchFail(exception: Exception)
    }



    fun fetchFavorites(listener: FavoritesFetchListener) {
        val favorites = mutableListOf<MovieModel>()
        for (i in 1..100) {
            favorites.add(MovieModel(i.toString(), "movieFavorite${i}"))
        }

        listener.onFavoritesFetchSuccess(favorites)
    }
}