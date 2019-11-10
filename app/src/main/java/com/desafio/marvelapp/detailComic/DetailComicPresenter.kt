package com.desafio.marvelapp.detailComic

interface DetailComicPresenter {
    fun getMarvelComic(id: Int)
    fun onDisposable()
}