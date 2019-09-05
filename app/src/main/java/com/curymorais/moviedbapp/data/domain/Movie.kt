package com.curymorais.moviedbapp.data.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie : Serializable {
    @SerializedName("popularity")
    var popularity: Double? = null

    @SerializedName("vote_count")
    var voteCount: Int? = null

    @SerializedName("video")
    var video: Boolean? = false

    @SerializedName("poster_path")
    var posterPath: String? =null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("adult")
    var adult: Boolean? = false

    @SerializedName("backdrop_path")
    var backdropPath: String? =null

    @SerializedName("original_language")
    var originalLanguage: String? =null

    @SerializedName("original_title")
    var originalTitle: String? =null

    @SerializedName("genre_ids")
    var genreIds: ArrayList<Int>? =null

    @SerializedName("title")
    var title: String? =null

    @SerializedName("vote_average")
    var voteAverage: Double? =null

    @SerializedName("overview")
    var overview: String? =null

    @SerializedName("release_date")
    var releaseDate: String? =null


}