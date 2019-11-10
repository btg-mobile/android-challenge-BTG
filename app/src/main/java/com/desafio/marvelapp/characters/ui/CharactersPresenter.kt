package com.desafio.marvelapp.characters.ui

import com.desafio.marvelapp.base.BasePresenterFavorite

interface CharactersPresenter : BasePresenterFavorite {
    fun loadMarvelCharacters(offset: Int)
    fun loadMarvelCharactersBeginLetter(letter: String)
    fun onDisposable()
}