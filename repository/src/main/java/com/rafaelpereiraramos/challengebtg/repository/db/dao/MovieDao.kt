package com.rafaelpereiraramos.challengebtg.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rafaelpereiraramos.challengebtg.repository.model.Movie

@Dao
interface MovieDao {

    @Insert
    fun createMovie(movie: Movie)

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovie(id: Int): LiveData<Movie?>

    fun getAllFavoutiresMovies(): LiveData<List<Movie>>
}