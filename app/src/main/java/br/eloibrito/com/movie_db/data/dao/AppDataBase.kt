package br.eloibrito.com.movie_db.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import br.eloibrito.com.movie_db.models.Movies

@Database(version = 2, entities = [Movies::class], exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getFavDAO() : MoviesDAO
}