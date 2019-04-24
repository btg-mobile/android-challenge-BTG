package com.uchoa.btg.movie.models.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.uchoa.btg.movie.models.Genre


class GenresResponse {

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null
}