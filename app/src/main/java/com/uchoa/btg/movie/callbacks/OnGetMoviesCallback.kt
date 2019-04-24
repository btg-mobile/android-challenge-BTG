package com.uchoa.btg.movie.callbacks

import com.uchoa.btg.movie.models.Movie

interface OnGetMoviesCallback {

    fun onSuccess(page: Int, movies: MutableList<Movie>)

    fun onError()
}