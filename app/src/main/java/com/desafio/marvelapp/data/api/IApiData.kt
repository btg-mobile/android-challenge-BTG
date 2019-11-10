package com.desafio.marvelapp.data.api

import com.desafio.marvelapp.model.MarvelCharacter
import com.desafio.marvelapp.model.MarvelComic
import io.reactivex.Single

interface IApiData {
    fun getMarvelCharacters(offset: Int): Single<List<MarvelCharacter>>
    fun getMarvelComics(offset: Int): Single<List<MarvelComic>>
    fun getDetailCharacter(id: Int?): Single<MarvelCharacter>
    fun getDetailComic(id: Int?): Single<MarvelComic>
    fun removeFavoriteCharacterCache(idMarvel: Int?)
    fun removeFavoriteComicCache(idMarvel: Int?)
    fun getMarvelCharactersBeginLetter(letter: String): Single<List<MarvelCharacter>>
    fun getMarvelComicsBeginLetter(letter: String): Single<List<MarvelComic>>
}