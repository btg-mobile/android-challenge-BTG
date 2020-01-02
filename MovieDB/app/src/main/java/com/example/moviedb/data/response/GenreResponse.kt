package com.example.moviedb.data.response

import com.google.gson.annotations.SerializedName

data class GenreResponse (

    @SerializedName("genres")
    val genreResults : List<GenreResults>
)