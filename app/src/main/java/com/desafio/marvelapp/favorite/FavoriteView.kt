package com.desafio.marvelapp.favorite

import com.desafio.marvelapp.base.BaseView
import com.desafio.marvelapp.model.Favorite

interface FavoriteView : BaseView {
    fun loadFavorites(favorites: Favorite)
}