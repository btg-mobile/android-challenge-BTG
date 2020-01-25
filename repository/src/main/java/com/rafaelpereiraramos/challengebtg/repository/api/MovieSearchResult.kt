package com.rafaelpereiraramos.challengebtg.repository.api

import com.google.gson.annotations.SerializedName
import com.rafaelpereiraramos.challengebtg.repository.model.Movie

data class MovieSearchResult(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int
)