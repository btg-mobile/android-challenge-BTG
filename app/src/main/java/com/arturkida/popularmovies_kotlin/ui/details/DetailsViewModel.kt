package com.arturkida.popularmovies_kotlin.ui.details

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.arturkida.popularmovies_kotlin.data.local.MovieRepository
import com.arturkida.popularmovies_kotlin.model.Movie

class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieRepository(application)
    private var allMovies = repository.getAllMovies()

    fun addMovie(movie: Movie) = repository.addMovie(movie)

    fun deleteMovie(movie: Movie) = repository.deleteMovie(movie)

    fun getAllMovies() = allMovies
}
