package com.desafio.marvelapp.data.repository

import com.desafio.marvelapp.model.Favorite
import io.reactivex.Flowable
import io.reactivex.Single

interface IRepositoryData {
    fun getAllFavorites(): Flowable<List<Favorite>>
    fun getCharactersFavorites(): Flowable<List<Favorite>>
    fun getComicsFavorites(): Flowable<List<Favorite>>
    fun insertFavorite(favorite: Favorite)
    fun deleteFavorite(favorite: Favorite): Single<Unit>

}