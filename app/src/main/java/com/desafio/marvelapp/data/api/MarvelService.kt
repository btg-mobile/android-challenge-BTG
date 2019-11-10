package com.desafio.marvelapp.data.api

import com.desafio.marvelapp.model.MarvelCharacterDataWrapper
import com.desafio.marvelapp.model.MarvelComicDataWrapper
import com.desafio.marvelapp.model.MarvelSeriesDataWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface MarvelService {

    @GET("v1/public/characters")
    fun getCharacters(@Query("limit") limit: Int, @Query("offset") offset: Int): Single<MarvelCharacterDataWrapper>

    @GET("v1/public/characters")
    fun getCharactersBeginLetter(@Query("limit") limit: Int, @Query("nameStartsWith") letter: String): Single<MarvelCharacterDataWrapper>

    @GET("v1/public/comics")
    fun getComics(@Query("limit") limit: Int, @Query("offset") offset: Int): Single<MarvelComicDataWrapper>

    @GET("v1/public/comics")
    fun getComicsBeginLetter(@Query("limit") limit: Int, @Query("titleStartsWith") titleStartsWith: String): Single<MarvelComicDataWrapper>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int?): Single<MarvelCharacterDataWrapper>

    @GET("/v1/public/comics/{comicId}")
    fun getComic(@Path("comicId") characterId: Int?): Single<MarvelComicDataWrapper>

    @GET
    fun getGenericComic(@Url url: String): Single<MarvelComicDataWrapper>

    @GET
    fun getGenericSeries(@Url url: String): Single<MarvelSeriesDataWrapper>

    @GET
    fun getGenericCharacter(@Url url: String): Single<MarvelCharacterDataWrapper>

}





