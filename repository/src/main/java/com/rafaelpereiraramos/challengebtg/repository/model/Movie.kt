package com.rafaelpereiraramos.challengebtg.repository.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @SerializedName("id") @PrimaryKey var id:  Int = 0,
    @SerializedName("favourite") var favourite: Boolean = false,
    @SerializedName("title") var title: String? = null,
    @SerializedName("poster_path") var coverUrl: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName( "genres") @Ignore var genres: List<Genre> = emptyList()
)