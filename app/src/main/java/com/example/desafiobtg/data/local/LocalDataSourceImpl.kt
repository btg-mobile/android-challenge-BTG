package com.example.desafiobtg.data.local

import android.arch.lifecycle.LiveData
import com.example.desafiobtg.AppController
import com.example.desafiobtg.db.entities.FavoriteMovie
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.db.room.Database
import com.example.desafiobtg.di.AppComponent
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(): LocalDataSource {

    @Inject
    lateinit var db: Database

    override fun insertMovies(movieList: List<Movie>) {
        AppController.runOnBG { db.movieModel().insertAll(movieList) }
    }

    override fun clearMovieTable() {
        AppController.runOnBG { db.movieModel().clear() }
    }

    override fun getLocalMovieList(page: Int): List<Movie>? {
        return db.movieModel().getMoviesForPage(page)
    }

    override fun addFavoriteMovie(movieId: String) {
        AppController.runOnBG { db.favoriteMovieModel().insert(FavoriteMovie(movieId)) }
    }

    override fun removeFavoriteMovie(movieId: String) {
        AppController.runOnBG { db.favoriteMovieModel().delete(FavoriteMovie(movieId)) }
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>?> {
        return db.favoriteMovieModel().getFavoriteMovies()
    }

    override fun getFavoriteIds(): LiveData<List<String>> {
        return db.favoriteMovieModel().getFavoriteIds()
    }
}