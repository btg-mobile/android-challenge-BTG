package com.desafio.marvelapp.base

import com.desafio.marvelapp.model.Favorite

interface BasePresenterFavorite {
    fun toggleFavorite(favorite: Favorite, checked: Boolean)
}