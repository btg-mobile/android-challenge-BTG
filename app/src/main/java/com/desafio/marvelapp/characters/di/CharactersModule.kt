package com.desafio.marvelapp.characters.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.desafio.marvelapp.characters.ui.CharactersPresenter
import com.desafio.marvelapp.characters.ui.CharactersPresenterImpl
import com.desafio.marvelapp.characters.ui.CharactersView

class CharactersModule(private val view: CharactersView) {

    val dependenciesKodein = Kodein.Module {
        bind<CharactersPresenter>() with provider {
            CharactersPresenterImpl(
                    view = view,
                    dataManager = instance())
        }
    }
}