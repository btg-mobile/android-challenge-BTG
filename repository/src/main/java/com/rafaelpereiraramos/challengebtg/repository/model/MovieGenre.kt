package com.rafaelpereiraramos.challengebtg.repository.model

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class MovieGenre(
    var movieId: Int = 0,
    var genreId: Int = 0
)