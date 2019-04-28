package com.example.desafiobtg.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class FavoriteMovie (@PrimaryKey @ColumnInfo(name = FAVORITE_ID) @SerializedName(Movie.ID) @Expose var id: String): Serializable {
    companion object {
        const val FAVORITE_ID = "favoriteId"
    }
}