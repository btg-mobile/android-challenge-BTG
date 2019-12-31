package com.example.moviedb.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ParcelCreator") @Parcelize
data class Movie (
    val id: Int,
    val title: String,
    var releaseDate: String,
    val overview: String,
    val voteAverage: Number,
    val posterPath: String,
    val genres: List<String>

) : Parcelable {
    fun getYear () : String {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.time = SimpleDateFormat("yyyy-MM-dd").parse(releaseDate)
        return calendar.get(Calendar.YEAR).toString()
    }
}

