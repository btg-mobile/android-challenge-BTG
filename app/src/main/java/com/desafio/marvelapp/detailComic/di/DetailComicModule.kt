package com.desafio.marvelapp.detailComic.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.desafio.marvelapp.detailCharacter.DetailComicView
import com.desafio.marvelapp.detailComic.DetailComicPresenter
import com.desafio.marvelapp.detailComic.DetailComicPresenterImpl

class DetailComicModule(private val view: DetailComicView) {

    val dependenciesKodein = Kodein.Module {
        bind<DetailComicPresenter>() with provider {
            DetailComicPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}