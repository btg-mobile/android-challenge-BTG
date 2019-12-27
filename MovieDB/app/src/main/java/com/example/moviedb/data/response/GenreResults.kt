package com.example.moviedb.data.response

import com.google.gson.annotations.SerializedName

data class GenreResults(

    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val title: String = ""

)