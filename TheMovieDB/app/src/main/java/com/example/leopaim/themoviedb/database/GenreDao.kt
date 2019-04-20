package com.example.leopaim.themoviedb.database


import android.arch.persistence.room.*
import com.example.leopaim.themoviedb.model.Genre

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun all(): List<Genre>

    @Query("SELECT title FROM genre WHERE id in (:genreIds)")
    fun getGenres(genreIds: String): List<String>

    @Query("SELECT title FROM genre WHERE id in (:genreId)")
    fun getGenre(genreId: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(genres: List<Genre>)

    @Delete
    fun delete(movie: Genre)

}