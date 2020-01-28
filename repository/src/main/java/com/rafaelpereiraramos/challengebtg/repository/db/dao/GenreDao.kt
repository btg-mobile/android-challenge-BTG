package com.rafaelpereiraramos.challengebtg.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafaelpereiraramos.challengebtg.repository.model.Genre

@Dao
interface GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createGenre(genre: Genre)

    @Query("SELECT * FROM genre WHERE id IN (:genresIds)")
    fun getGenres(genresIds: List<String>): LiveData<List<Genre>>
}