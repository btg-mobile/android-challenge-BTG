package com.example.moviedb.presentation.repository

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviedb.data.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object DataRepository {

    private var moviesLiveData: MutableList<Movie> = mutableListOf()
    private var popularLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private var favoriteLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private var favoriteMoviesIds: MutableList<Int> = mutableListOf()

    fun restorePopularData() {
        popularLiveData.value = moviesLiveData
    }

    fun createPopularObserver(owner: LifecycleOwner, observer: Observer<List<Movie>>) {
        popularLiveData.observe(owner, observer)
    }

    fun createFavoriteObserver(owner: LifecycleOwner, observer: Observer<List<Movie>>) {
        favoriteLiveData.observe(owner, observer)
    }

    fun setMoviesData(moviesList: MutableList<Movie>) {

        moviesLiveData.addAll(moviesList)

        restorePopularData()
    }

    fun toggleFavoriteMovie(id: Int?, context: Context?): Boolean {

        if (favoriteMoviesIds.any { e -> e == id }) {
            favoriteMoviesIds.remove(id!!)
            saveData(context)
            return false
        } else {

            favoriteMoviesIds.add(id!!)
            saveData(context)
            return true
        }
    }

    private fun saveData(context: Context?) {

        val sharedPreferences = context?.getSharedPreferences("movies_list", 0)

        sharedPreferences?.let {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(favoriteMoviesIds)
            json?.let {
                editor?.putString("favorite list", json)
                editor?.apply()
            }

        }

    }

    fun loadData(context: Context?) {

        val sharedPreferences = context?.getSharedPreferences("movies_list", 0)

        sharedPreferences?.let {
            val gson = Gson()
            val json = sharedPreferences.getString("favorite list", null)
            val type = object : TypeToken<MutableList<Int>>() {}.type
            json?.let {
                favoriteMoviesIds = gson.fromJson(json, type)
            }

        }
    }

    @SuppressLint("DefaultLocale")
    fun searchMovieByTitle(title: String) {
        popularLiveData.value =
            moviesLiveData?.filter { it.title.toLowerCase().contains(title) }

    }

    fun restoreFavoriteList() {

        favoriteLiveData.value =
            moviesLiveData.filter { movie ->
                isFavorite(movie.id)
            }
    }

    fun isFavoriteEmpty(): Boolean = favoriteLiveData.value.isNullOrEmpty()

    fun isFavorite(id: Int?): Boolean = favoriteMoviesIds.any { e -> e == id }

    @SuppressLint("DefaultLocale")
    fun searchFavoriteMoviesByYearOrTitle(query: String) {

        val value = moviesLiveData.filter { movie ->
            isFavorite(movie.id) && (movie.title.toLowerCase().contains(query) ||
                    movie.getYear().toLowerCase().contains(query))

        }

        favoriteLiveData.value = value


    }
}



