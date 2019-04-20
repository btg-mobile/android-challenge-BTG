package com.example.leopaim.themoviedb.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "Genre")
data class Genre (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val name: String)



data class GenreResponse (
    val genres: List<Genre>
)
