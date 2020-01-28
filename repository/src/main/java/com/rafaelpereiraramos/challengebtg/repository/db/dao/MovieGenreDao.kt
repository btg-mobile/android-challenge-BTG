package com.rafaelpereiraramos.challengebtg.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.rafaelpereiraramos.challengebtg.repository.model.MovieGenre

@Dao
interface MovieGenreDao {

    @Insert
    fun createMovieGenre(movieGenre: MovieGenre)
}