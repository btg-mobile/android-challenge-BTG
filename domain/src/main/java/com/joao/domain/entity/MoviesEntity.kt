package com.joao.domain.entity

data class MoviesEntity(
    val page: Int,
    val totalPages: Int,
    val movies: ArrayList<MoviesItem?>,
    var totalResults: Int
)