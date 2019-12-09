package com.brunoalmeida.movies.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brunoalmeida.movies.data.model.Genre
import java.util.*

@Dao
interface GenreDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE) fun insertGenres(vararg movies: Genre)

    @Query("SELECT * FROM genre WHERE id = :id ") fun findGenreById(id: Int): Optional<Genre>
}