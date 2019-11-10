package com.desafio.marvelapp.characters.ui

import com.desafio.marvelapp.base.BaseView
import com.desafio.marvelapp.model.MarvelCharacter

interface CharactersView : BaseView {
    fun loadCharacters(marvelCharacters: List<MarvelCharacter>)
    fun loadFilterCharacters(marvelCharacters: List<MarvelCharacter>)
}