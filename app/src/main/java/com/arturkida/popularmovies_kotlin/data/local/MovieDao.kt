package com.arturkida.popularmovies_kotlin.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.arturkida.popularmovies_kotlin.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>

    @Insert (onConflict = REPLACE)
    fun addMovie(vararg movie: Movie)

    @Delete
    fun deleteMovie(vararg movie: Movie)
}