package com.example.moviedb.presentation.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie

object DataRepository {

    var moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMoviesData(): MutableLiveData<List<Movie>> {
        return moviesLiveData
    }

    fun setMoviesData(moviesList: MutableList<Movie>) {
        this.moviesLiveData.value = moviesList
    }

    fun toggleFavoriteMovie(id: Int): Boolean {
        val favorite = !moviesLiveData.value?.find { e -> e.id == id }?.favorite!!

        moviesLiveData.value?.find { e -> e.id == id }?.favorite = favorite

        return favorite
    }

    fun getFavoriteMovies(): MutableList<Movie> {
        val moviesList: MutableList<Movie> = mutableListOf()

        moviesLiveData.value?.let { movies ->

            for (movie in movies) {
                if (movie.favorite)
                    moviesList.add(movie)
            }

        }



        return moviesList

    }

}




