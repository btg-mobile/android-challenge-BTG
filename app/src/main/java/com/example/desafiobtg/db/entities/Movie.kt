package com.example.desafiobtg.db.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity
data class Movie(@PrimaryKey @ColumnInfo(name = MOVIE_ID) @Expose var movieId: String) {

    companion object {
        const val MOVIE_ID = "movieId"
    }
}