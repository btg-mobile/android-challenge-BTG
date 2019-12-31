package com.example.moviedb.presentation.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.moviedb.data.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object DataRepository {

    private var moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private var favoriteMoviesIds: MutableList<Int> = mutableListOf()

    fun getMoviesData(): MutableLiveData<List<Movie>> {
        return moviesLiveData
    }

    fun setMoviesData(moviesList: MutableList<Movie>) {
        this.moviesLiveData.value = moviesList
    }

    fun toggleFavoriteMovie(id: Int?, context: Context?): Boolean {

        return if (favoriteMoviesIds.any { e -> e == id }) {
            favoriteMoviesIds.remove(id!!)
            saveData(context )
            false
        } else {

            favoriteMoviesIds.add(id!!)
            saveData(context )
            true
        }
    }

    private fun saveData(context: Context?) {

        val sharedPreferences = context?.getSharedPreferences("movies_list", 0)

        sharedPreferences?.let {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(favoriteMoviesIds)
            json?.let{
                editor?.putString("favorite list", json)
                editor?.apply()
            }

        }

    }

    fun loadData(context: Context?) {

        val sharedPreferences = context?.getSharedPreferences("movies_list", 0)

        sharedPreferences?.let {
            val gson  = Gson()
            val json = sharedPreferences.getString("favorite list", null)
            val type = object : TypeToken<MutableList<Int>>() {}.type
            json?.let{
                favoriteMoviesIds = gson.fromJson(json, type)
            }

        }


    }

    fun getFavoriteMovies(): MutableList<Movie> {
        val moviesList: MutableList<Movie> = mutableListOf()

        moviesLiveData.value?.let { movies ->

            for (movie in movies) {
                if (favoriteMoviesIds.any { e -> e == movie.id })
                    moviesList.add(movie)
            }
        }

        return moviesList
    }

    fun isFavorite(id: Int?): Boolean = favoriteMoviesIds.any { e -> e == id }

    @SuppressLint("DefaultLocale")
    fun searchFavoriteMoviesByYearOrTitle(query: String): MutableList<Movie> {
        val moviesList: MutableList<Movie> = mutableListOf()

        moviesLiveData.value?.let { movies ->

            for (movie in movies) {
                if (
                    favoriteMoviesIds.any { e -> e == movie.id } &&
                    (movie.title.toLowerCase().contains(query) ||
                            (movie.getYear().toLowerCase().contains(query))))
                    moviesList.add(movie)
            }
        }

        return moviesList

    }

}




