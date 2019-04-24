package com.uchoa.btg.movie.models.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.uchoa.btg.movie.models.Trailer


class TrailerResponse {

    @SerializedName("results")
    @Expose
    var trailers: List<Trailer>? = null
}