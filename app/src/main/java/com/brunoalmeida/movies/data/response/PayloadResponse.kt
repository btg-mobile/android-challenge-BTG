package com.brunoalmeida.movies.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PayloadResponse (
    @Json(name = "page")
    val page: Int,

    @Json(name = "results")
    val results: List<MovieResponse>,

    @Json(name = "total_results")
    val totalResults: Int,

    @Json(name = "total_pages")
    val totalPages: Int
)