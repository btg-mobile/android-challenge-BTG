package com.example.moviedb.data.response

import com.google.gson.annotations.SerializedName

data class MovieResponse (

    @SerializedName("results")
    val movieResults : List<MovieResults>
)