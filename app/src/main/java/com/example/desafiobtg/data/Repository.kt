package com.example.desafiobtg.data

import android.arch.lifecycle.LiveData
import android.content.Context
import com.example.desafiobtg.AppController
import com.example.desafiobtg.data.local.LocalDataSource
import com.example.desafiobtg.data.remote.PopularMoviesResponse
import com.example.desafiobtg.data.remote.RemoteDataSource
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.di.qualifiers.utils.ApplicationContext
import com.example.desafiobtg.manager.AppDataManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource,
                                     private val localDataSource: LocalDataSource,
                                     private val appDataManager: AppDataManager,
                                     @ApplicationContext private val context: Context) {

    fun getMovieList(page: Int, success: (response: PopularMoviesResponse) -> Unit, failure: (error: String?) -> Unit) {
        if (appDataManager.shouldClearLocalMovieData) {
            localDataSource.clearMovieTable()
            getRemotePopularMovieList(page, success, failure)
            return
        }

        AppController.runOnBG {
            localDataSource.getLocalMovieList(page)?.takeIf { it.isNotEmpty() }?.let {
                success(PopularMoviesResponse(page, it))
            } ?: run {
                getRemotePopularMovieList(page, success, failure)
            }
        }
    }

    fun getRemotePopularMovieList(page: Int, success: (response: PopularMoviesResponse) -> Unit, failure: (error: String?) -> Unit) {
        remoteDataSource.getMovieList(page, { response ->
            localDataSource.insertMovies(response.movieList)
            success(response)
        }, { error ->
            failure(error)
        })
    }

    fun addFavoriteMovie(movieId: String) {
        return localDataSource.addFavoriteMovie(movieId)
    }

    fun removeFavoriteMovie(movieId: String) {
        return localDataSource.removeFavoriteMovie(movieId)
    }

    fun getFavoriteMovieList() : LiveData<List<Movie>?> {
        return localDataSource.getFavoriteMovies()
    }

    fun getFavoriteIds() : LiveData<List<String>> {
        return localDataSource.getFavoriteIds()
    }
}