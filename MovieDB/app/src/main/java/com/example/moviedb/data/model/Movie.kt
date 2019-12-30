package com.example.moviedb.data.model

data class Movie (
    val id: Int,
    val title: String,
    val year: String,
    val overview: String,
    val voteAverage: Number,
    val posterPath: String,
    var favorite: Boolean = false
//    val genres: List<String>
)