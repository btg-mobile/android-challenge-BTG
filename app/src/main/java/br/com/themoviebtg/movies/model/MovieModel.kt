package br.com.themoviebtg.movies.model

import java.io.Serializable
import java.math.BigDecimal


data class MovieModel(
    val id: Long,
    val original_title: String?,
    val poster_path: String?,
    val title: String?,
    val backdrop_path: String,
    val voteAverage: BigDecimal?,
    val overview: String,
    val vote_average: Float,
    val release_date: String?,
    var isFavorite: Boolean
) : Serializable {
    val longId = 1L

}