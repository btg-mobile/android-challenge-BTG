package com.desafio.marvelapp.data.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.desafio.marvelapp.model.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}