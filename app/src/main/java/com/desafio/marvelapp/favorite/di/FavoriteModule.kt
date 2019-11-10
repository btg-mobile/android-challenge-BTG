package com.desafio.marvelapp.favorite.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.desafio.marvelapp.favorite.FavoritePresenter
import com.desafio.marvelapp.favorite.FavoritePresenterImpl
import com.desafio.marvelapp.favorite.FavoriteView

class FavoriteModule(private val view: FavoriteView) {

    val dependenciesKodein = Kodein.Module {
        bind<FavoritePresenter>() with provider {
            FavoritePresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}