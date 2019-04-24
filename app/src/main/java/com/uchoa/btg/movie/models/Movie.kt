package com.uchoa.btg.movie.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Movie {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdrop: String? = null

    @SerializedName("vote_average")
    @Expose
    var rating: Float = 0.toFloat()

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null

    var favorite: Boolean = false
}