package com.rafaelpereiraramos.challengebtg.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.model.MovieGenre

@Dao
interface MovieDao {

    @Insert
    fun createMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovie(id: Int): LiveData<Movie?>

    @Query("SELECT * FROM MovieGenre WHERE movieId = :id")
    fun getAllMovieGenre(id: Int): LiveData<List<MovieGenre>>

    @Query("UPDATE Movie SET favourite = :favourite WHERE id = :id")
    fun updateFavourite(id: Int, favourite: Boolean)

    @Query("SELECT * FROM Movie WHERE favourite = :favourite")
    fun getMoviesByFavourite(favourite: Boolean = true): LiveData<List<Movie>>
}