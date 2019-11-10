package com.desafio.marvelapp.detailCharacter.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.desafio.marvelapp.detailCharacter.ui.DetailCharacterPresenter
import com.desafio.marvelapp.detailCharacter.ui.DetailCharacterPresenterImpl
import com.desafio.marvelapp.detailCharacter.ui.DetailCharacterView

class DetailCharacterModule(private val view: DetailCharacterView) {

    val dependenciesKodein = Kodein.Module {
        bind<DetailCharacterPresenter>() with provider {
            DetailCharacterPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}