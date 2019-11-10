package com.desafio.marvelapp.detailCharacter.ui

interface DetailCharacterPresenter {
    fun getMarvelCharacter(id: Int)
    fun onDisposable()
}