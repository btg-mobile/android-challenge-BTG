package com.curymorais.twitchtop100.data.domain

import com.curymorais.moviedbapp.data.domain.Movie
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TopMovies : Serializable {
    @SerializedName("page")
    var page: Int? = null

    @SerializedName("total_results")
    var totalResults: Int? = null

    @SerializedName("total_pages")
    var totalPages: Int? = null

    @SerializedName("results")
    var results: ArrayList<Movie>? = null

}