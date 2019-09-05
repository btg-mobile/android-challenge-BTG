package com.curymorais.moviedbapp.moviedetail

import com.curymorais.moviedbapp.data.domain.Movie
import com.curymorais.moviedbapp.data.repository.local.MoviePreferences

class MovieDetailPresenter (movieDetailFragment: MovieDetailFragment, movieDetail :Movie) {

    var mView = movieDetailFragment
    var moviePreferences: MoviePreferences
    var movieDetail = movieDetail
    var favoriteMovies : HashMap<Int, String>? = null

    init{
        mView = movieDetailFragment
        moviePreferences = MoviePreferences(mView.context!!)
        favoriteMovies = if (moviePreferences.getValueString("movies") != null) {
            moviePreferences.jsonToHash(moviePreferences.getValueString("movies")!!)
        } else {
            HashMap()
        }
    }

    fun isMovieFavorited() : Boolean {
        return favoriteMovies!!.contains(movieDetail.id)
    }

    fun favoriteMovie(){

        if(isMovieFavorited()) {
            favoriteMovies!!.remove(movieDetail.id!!)
        } else {
            favoriteMovies!!.put(movieDetail.id!!, movieDetail.title!!)
        }
        moviePreferences.save("movies",moviePreferences.hashToJson(favoriteMovies!!))
    }
}