package br.eloibrito.com.movie_db.di.module

import android.content.Context
import androidx.room.Room
import br.eloibrito.com.movie_db.data.dao.AppDataBase
import dagger.Module
import dagger.Provides

@Module
class RoomModulo(private val context: Context) {

    @Provides
    fun appContext() = context

    @Provides
    fun dataBase(context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "movie-db")
            .allowMainThreadQueries().build()
}