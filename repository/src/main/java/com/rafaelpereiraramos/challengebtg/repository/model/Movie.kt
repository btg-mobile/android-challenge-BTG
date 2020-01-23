package com.rafaelpereiraramos.challengebtg.repository.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id:  Int,
    @SerializedName("favourite") val boolean: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val coverUrl: String,
    @SerializedName("release_date") val releaseDate: String
)