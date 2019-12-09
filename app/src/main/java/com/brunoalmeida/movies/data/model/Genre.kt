package com.brunoalmeida.movies.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
) : Serializable