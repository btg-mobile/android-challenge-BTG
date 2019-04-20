package com.example.leopaim.themoviedb.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.leopaim.themoviedb.model.Genre
import com.example.leopaim.themoviedb.model.Movie

@Database(entities = [Movie::class, Genre::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}
