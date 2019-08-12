package com.arturkida.popularmovies_kotlin.data.local

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants

class MovieRepository(context: Context) {

    private var movieDao: MovieDao?
    var allFavoriteMovies: LiveData<List<Movie>>?

    init {
        val database = AppDatabase.getAppDatabase(context)
        movieDao = database?.movieDao()
        allFavoriteMovies = movieDao?.getAllMovies()
    }

    fun addMovie(movie: Movie) {
        InsertMovieAsyncTask(movieDao).execute(movie)
    }

    fun deleteMovie(movie: Movie) {
        DeleteMovieAsyncTask(movieDao).execute(movie)
    }

    private class InsertMovieAsyncTask(private val movieDao: MovieDao?) : AsyncTask<Movie, Unit, Unit>() {
        override fun doInBackground(vararg params: Movie) {
            Log.i(Constants.LOG_INFO, "Adding movie to database")
            movieDao?.addMovie(params[0])
        }
    }

    private class DeleteMovieAsyncTask(private val movieDao: MovieDao?) : AsyncTask<Movie, Unit, Unit>() {
        override fun doInBackground(vararg params: Movie) {
            Log.i(Constants.LOG_INFO, "Removing movie from database")
            movieDao?.deleteMovie(params[0])
        }
    }
}