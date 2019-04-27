package com.example.desafiobtg.di

import android.app.Application
import android.arch.persistence.room.Room
import com.example.desafiobtg.db.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDb(application: Application): Database {
        return Room.databaseBuilder(application,
            Database::class.java,
            Database.DB_NAME).
            build()
    }
}