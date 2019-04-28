package com.example.desafiobtg.data.local

import android.arch.lifecycle.LiveData
import com.example.desafiobtg.db.entities.FavoriteMovie
import com.example.desafiobtg.db.entities.Movie

interface LocalDataSource {
    fun insertMovies(movieList: List<Movie>)
    fun getLocalMovieList(page: Int): List<Movie>?
    fun clearMovieTable()

    fun addFavoriteMovie(movieId: String)
    fun removeFavoriteMovie(movieId: String)
    fun getFavoriteMovies(): LiveData<List<Movie>?>
    fun getFavoriteIds(): LiveData<List<String>>
}