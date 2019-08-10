package com.arturkida.popularmovies_kotlin.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.arturkida.popularmovies_kotlin.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(GenresTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}