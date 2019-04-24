package com.uchoa.btg.movie.callbacks

import com.uchoa.btg.movie.models.Trailer

interface OnGetTrailersCallback {
    fun onSuccess(trailers: List<Trailer>)

    fun onError()
}