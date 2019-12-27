package com.example.moviedb.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MovieResults (

    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Number,

    @Expose(deserialize = false) // deserialize is this field is not required
    @SerializedName("poster_path")
    val posterPath: String = ""


)