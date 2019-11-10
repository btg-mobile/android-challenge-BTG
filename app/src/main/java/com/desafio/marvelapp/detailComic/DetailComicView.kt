package com.desafio.marvelapp.detailCharacter


import com.desafio.marvelapp.base.BaseView
import com.desafio.marvelapp.model.MarvelComic

interface DetailComicView : BaseView {
    fun loadComic(marvelComic: MarvelComic)
}