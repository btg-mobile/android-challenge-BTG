package com.rafaelpereiraramos.challengebtg.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @SerializedName("id") @PrimaryKey var id:  Int = 0,
    @SerializedName("favourite") var boolean: Boolean = false,
    @SerializedName("title") var title: String? = null,
    @SerializedName("poster_path") var coverUrl: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null
)