package com.desafio.marvelapp.favorite

import com.desafio.marvelapp.model.Favorite

interface FavoritePresenter {
    fun loadFavorites()
    fun deleteFavorite(favorite: Favorite, removeCharacter: Boolean)
    fun onDisposable()
}