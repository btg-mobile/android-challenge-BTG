package com.example.leopaim.themoviedb.database

import android.arch.persistence.room.*
import android.support.design.circularreveal.CircularRevealHelper
import com.example.leopaim.themoviedb.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun all(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT EXISTS(SELECT 1 FROM movie WHERE id= :id LIMIT 1)")
    fun hasFavorite(id: String): Boolean

}