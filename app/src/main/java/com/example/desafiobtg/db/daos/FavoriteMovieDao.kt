package com.example.desafiobtg.db.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.example.desafiobtg.db.entities.FavoriteMovie
import com.example.desafiobtg.db.entities.Movie

@Dao
interface FavoriteMovieDao : BaseDao<FavoriteMovie> {

    @Query("SELECT movie.* FROM movie JOIN favoritemovie ON favoritemovie.favoriteId = movie.id")
    fun getFavoriteMovies(): LiveData<List<Movie>?>

    @Query("SELECT favoriteId FROM favoritemovie")
    fun getFavoriteIdsLiveData(): LiveData<List<String>>

    @Query("SELECT favoriteId FROM favoritemovie")
    fun getFavoriteIds(): List<String>

}