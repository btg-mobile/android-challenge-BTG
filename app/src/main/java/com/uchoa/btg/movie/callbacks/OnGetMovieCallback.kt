package com.uchoa.btg.movie.callbacks

import com.uchoa.btg.movie.models.Movie

interface OnGetMovieCallback {

    fun onSuccess(movie: Movie)

    fun onError()
}