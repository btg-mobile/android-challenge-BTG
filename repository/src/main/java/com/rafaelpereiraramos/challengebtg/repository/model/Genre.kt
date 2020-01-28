package com.rafaelpereiraramos.challengebtg.repository.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Genre(
    @SerializedName("id") @PrimaryKey var id: Int = 0,
    @SerializedName("name") var name: String = ""
)