package br.eloibrito.com.movie_db.models

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

data class Generos (
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,

    @Ignore var checked : Boolean? = false
)