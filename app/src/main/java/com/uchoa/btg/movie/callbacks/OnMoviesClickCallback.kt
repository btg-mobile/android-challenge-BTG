package com.uchoa.btg.movie.callbacks

import android.view.View
import com.uchoa.btg.movie.models.Movie

interface OnMoviesClickCallback {
    fun onCellClick(title: View, rate: View, movie: Movie)

    fun onRateClick(movie: Movie)
}