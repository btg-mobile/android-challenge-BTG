package com.rafaelpereiraramos.challengebtg.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelpereiraramos.challengebtg.repository.db.dao.GenreDao
import com.rafaelpereiraramos.challengebtg.repository.db.dao.MovieDao
import com.rafaelpereiraramos.challengebtg.repository.db.dao.MovieGenreDao
import com.rafaelpereiraramos.challengebtg.repository.model.Genre
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.model.MovieGenre

@Database(entities = [Movie::class, Genre::class, MovieGenre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun movieGenreDao(): MovieGenreDao
}