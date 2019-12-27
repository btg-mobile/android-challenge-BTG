package com.example.moviedb.data.model

data class Movie (
    val title: String,
    val year: String,
    val overview: String,
    val voteAverage: Number,
    val posterPath: String
//    val genres: List<String>
)