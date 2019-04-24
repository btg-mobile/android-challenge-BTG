package com.uchoa.btg.movie.models.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.uchoa.btg.movie.models.Movie

class MoviesResponse {

    @SerializedName("page")
    @Expose
    var page: Int = 0

    @SerializedName("total_results")
    @Expose
    var totalResults: Int = 0

    @SerializedName("results")
    @Expose
    var movies: MutableList<Movie>? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0
}