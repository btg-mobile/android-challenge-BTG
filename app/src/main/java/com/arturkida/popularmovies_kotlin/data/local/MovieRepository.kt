package com.arturkida.popularmovies_kotlin.data.local

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.arturkida.popularmovies_kotlin.model.Movie

class MovieRepository(application: Application) {

    private var movieDao: MovieDao?
    private var allMovies: LiveData<List<Movie>>?

    init {
        val database = AppDatabase.getAppDatabase(application.applicationContext)
        movieDao = database?.movieDao()
        allMovies = movieDao?.getAllMovies()
    }

    fun addMovie(movie: Movie) {
        InsertMovieAsyncTask(movieDao).execute(movie)
    }

    fun deleteMovie(movie: Movie) {
        DeleteMovieAsyncTask(movieDao).execute(movie)
    }

    fun getAllMovies(): LiveData<List<Movie>>? = allMovies

    private class InsertMovieAsyncTask(private val movieDao: MovieDao?) : AsyncTask<Movie, Unit, Unit>() {
        override fun doInBackground(vararg params: Movie) {
            movieDao?.addMovie(params[0])
        }
    }

    private class DeleteMovieAsyncTask(private val movieDao: MovieDao?) : AsyncTask<Movie, Unit, Unit>() {
        override fun doInBackground(vararg params: Movie) {
            movieDao?.deleteMovie(params[0])
        }
    }
}