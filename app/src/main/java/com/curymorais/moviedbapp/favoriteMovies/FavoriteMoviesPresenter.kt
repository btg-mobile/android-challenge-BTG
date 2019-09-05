package com.curymorais.moviedbapp.favoriteMovies

import com.curymorais.moviedbapp.data.repository.local.MoviePreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class FavoriteMoviesPresenter(favoriteMoviesFragment: FavoriteMoviesFragment) {

    var mView = favoriteMoviesFragment
    lateinit var moviePreferences : MoviePreferences

    fun getData() {
        moviePreferences = MoviePreferences(mView.context!!)
        var movies = ArrayList<String>()

        if (moviePreferences.getValueString("movies") != null) {
            for (i in moviePreferences.jsonToHash(moviePreferences.getValueString("movies")!!)){
                movies.add(i.value)
            }
        }

        onSuccess(movies)
    }

    fun onSuccess(movieArrayList: ArrayList<String>?) {
        mView?.updateView(movieArrayList)

    }
}


