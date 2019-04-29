package com.example.desafiobtg.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.desafiobtg.db.converters.GeneralTypeConverter
import com.example.desafiobtg.db.daos.FavoriteMovieDao
import com.example.desafiobtg.db.daos.GenreDao
import com.example.desafiobtg.db.daos.MovieDao
import com.example.desafiobtg.db.entities.FavoriteMovie
import com.example.desafiobtg.db.entities.Genre
import com.example.desafiobtg.db.entities.Movie

@Database(entities = [Movie::class, FavoriteMovie::class, Genre::class], version = 1, exportSchema = false)
@TypeConverters(GeneralTypeConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun favoriteMovieModel(): FavoriteMovieDao
    abstract fun movieModel(): MovieDao
    abstract fun genreModel(): GenreDao

    companion object {
        const val DB_NAME = "DesafioBTG_db"
    }

}