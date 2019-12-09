package com.brunoalmeida.movies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brunoalmeida.movies.data.dao.GenreDao
import com.brunoalmeida.movies.data.dao.MovieDao
import com.brunoalmeida.movies.data.model.Genre
import com.brunoalmeida.movies.data.model.Movie

@Database(version = 3, entities = arrayOf(Movie::class, Genre::class), exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract  fun genreDao(): GenreDao
}