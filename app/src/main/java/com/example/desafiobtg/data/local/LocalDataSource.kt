package com.example.desafiobtg.data.local

import android.arch.lifecycle.LiveData
import com.example.desafiobtg.db.entities.FavoriteMovie
import com.example.desafiobtg.db.entities.Genre
import com.example.desafiobtg.db.entities.Movie

interface LocalDataSource {
    fun insertMovies(movieList: List<Movie>)
    fun getLocalMovieList(page: Int): List<Movie>?
    fun clearMovieTable()

    fun addFavoriteMovie(movieId: String)
    fun removeFavoriteMovie(movieId: String)
    fun getFavoriteMovies(): LiveData<List<Movie>?>
    fun getFavoriteIdsLiveData(): LiveData<List<String>>
    fun getFavoriteIds(): List<String>
    fun getMovieById(movieId: String?, success: (result: Movie?) -> Unit)
    fun addGenres(genres: List<Genre>)
    fun getGenreByIds(genreIds: List<Int>): List<String>
}