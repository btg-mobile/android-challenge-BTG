package com.desafio.marvelapp.comics.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.desafio.marvelapp.comics.ui.ComicsPresenter
import com.desafio.marvelapp.comics.ui.ComicsPresenterImpl
import com.desafio.marvelapp.comics.ui.ComicsView


class ComicModule(private val view: ComicsView) {

    val dependenciesKodein = Kodein.Module {
        bind<ComicsPresenter>() with provider {
            ComicsPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}