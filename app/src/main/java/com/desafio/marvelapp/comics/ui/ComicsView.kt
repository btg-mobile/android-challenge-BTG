package com.desafio.marvelapp.comics.ui

import com.desafio.marvelapp.base.BaseView
import com.desafio.marvelapp.model.MarvelComic

interface ComicsView : BaseView {
    fun loadComics(comics: List<MarvelComic>)
    fun loadFilterComics(comics: List<MarvelComic>)
}