package br.com.questv.themoviebtg.favorites.interaction

import br.com.questv.themoviebtg.movies.model.MovieModel

class FavoritesInteractor {
    interface FavoritesFetchListener {
        fun onFavoritesFetchSuccess(favoritesModelList: List<MovieModel>)
        fun onFavoritesFetchFail(exception: Exception)
    }



    fun fetchFavorites(listener: FavoritesFetchListener) {
        listener.onFavoritesFetchSuccess(listOf(
            MovieModel("1", "movieFavorite1"),
            MovieModel("2", "movieFavorite2"),
            MovieModel("3", "movieFavorite3")
        ))


    }
}