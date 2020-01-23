package com.rafaelpereiraramos.challengebtg.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafaelpereiraramos.challengebtg.repository.db.dao.MovieDao
import com.rafaelpereiraramos.challengebtg.repository.model.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}