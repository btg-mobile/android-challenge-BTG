package com.arturkida.popularmovies_kotlin.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.arturkida.popularmovies_kotlin.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun all(): List<Movie>

    @Insert
    fun add(vararg movie: Movie)
}