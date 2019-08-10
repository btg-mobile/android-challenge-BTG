package com.arturkida.popularmovies_kotlin.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants.Companion.MOVIES_DATABASE

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(GenresTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase? {
            if(INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        MOVIES_DATABASE)
                        .build()
                }
            }

            return INSTANCE
        }

        fun destroyDatabase() {
            INSTANCE = null
        }
    }
}