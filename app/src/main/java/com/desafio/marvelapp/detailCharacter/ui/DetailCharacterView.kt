package com.desafio.marvelapp.detailCharacter.ui


import com.desafio.marvelapp.base.BaseView
import com.desafio.marvelapp.model.MarvelCharacter

interface DetailCharacterView : BaseView {
    fun loadCharacter(marvelCharacter: MarvelCharacter)
}