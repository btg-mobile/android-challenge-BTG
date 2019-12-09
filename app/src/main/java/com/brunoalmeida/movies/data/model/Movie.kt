package com.brunoalmeida.movies.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val uuid: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "poster_path") @SerializedName("poster_path")
    val posterPath: String,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "vote_average")
    val voteAverage: String?,

    @ColumnInfo(name = "genre_ids")
    val genreIds: String?
) : Serializable