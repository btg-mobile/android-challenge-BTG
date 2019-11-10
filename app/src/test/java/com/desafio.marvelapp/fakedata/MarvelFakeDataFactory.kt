package com.desafio.marvelapp.fakedata

import com.desafio.marvelapp.model.MarvelCharacter
import com.desafio.marvelapp.model.MarvelCharacterDataContainer
import com.desafio.marvelapp.model.MarvelCharacterDataWrapper


object MarvelFakeDataFactory {

    val fakeMarvelCharacterDataWrapperId2 by lazy {
        val marvelCharacterDataWrapper = MarvelCharacterDataWrapper()
        marvelCharacterDataWrapper.data = getMarvelCharacterDataContainer()
        marvelCharacterDataWrapper.data?.results = arrayListOf(MarvelCharacter(2))
        marvelCharacterDataWrapper
    }

    private fun getMarvelCharacterDataContainer(): MarvelCharacterDataContainer {
        return MarvelCharacterDataContainer()
    }

}