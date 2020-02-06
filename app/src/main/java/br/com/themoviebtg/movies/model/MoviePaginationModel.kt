package br.com.themoviebtg.movies.model

data class MoviePaginationModel(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: List<MovieModel>
)