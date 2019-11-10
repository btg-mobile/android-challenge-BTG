package com.desafio.marvelapp.comics.ui

import com.desafio.marvelapp.base.BasePresenterFavorite

interface ComicsPresenter : BasePresenterFavorite{
    fun loadMarvelComics(offset: Int)
    fun loadMarvelComicsBeginLetter(letter: String)
    fun onDisposable()
}