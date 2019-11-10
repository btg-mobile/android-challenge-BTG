package com.desafio.marvelapp.data.repository

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.desafio.marvelapp.model.Favorite
import io.reactivex.Flowable

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): Flowable<List<Favorite>>

    @Insert
    fun insert(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE ${KeyDatabase.GROUP_TYPE} = ${KeyDatabase.FavoriteGroup.CHARACTERS}")
    fun getCharactersFavorites(): Flowable<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE ${KeyDatabase.GROUP_TYPE} = ${KeyDatabase.FavoriteGroup.COMICS}")
    fun getComicsFavorites(): Flowable<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE ${KeyDatabase.ID} = :idMarvel ")
    fun getFavorite(idMarvel: Int?): Flowable<Favorite>


    @Delete
    fun delete(favorite: Favorite)
}