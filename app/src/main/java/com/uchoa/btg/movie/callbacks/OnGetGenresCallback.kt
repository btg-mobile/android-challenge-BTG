package com.uchoa.btg.movie.callbacks

import com.uchoa.btg.movie.models.Genre

interface OnGetGenresCallback {

    fun onSuccess(genres: List<Genre>)

    fun onError()
}