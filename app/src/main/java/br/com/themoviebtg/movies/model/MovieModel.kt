package br.com.themoviebtg.movies.model

import java.math.BigDecimal


data class MovieModel(
    val id: Long,
    val original_title: String?,
    val poster_path: String?,
    val title: String?,
    val voteAverage: BigDecimal?
) {
    val longId = 1L

}