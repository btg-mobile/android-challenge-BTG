package com.example.leopaim.themoviedb.model

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.Context
import android.os.Parcelable
import com.example.leopaim.themoviedb.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Movie") @SuppressLint("ParcelCreator") @Parcelize
data class Movie (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "vote_average") val vote_average: String?,
    @ColumnInfo(name = "genre_ids") val genre_ids: ArrayList<String>?,
    @ColumnInfo(name = "overview")val overview: String?,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") var poster: String? ) : Parcelable {

        fun getYear(): String? {
            if(release_date.equals("") || release_date == null){
                return ""
            }else{
                val calendar = Calendar.getInstance(TimeZone.getDefault())
                calendar.setTime(SimpleDateFormat("yyyy-MM-dd").parse(release_date))
                return calendar.get(Calendar.YEAR).toString()
            }
        }

        fun getSafeTitle(context: Context): String {
            if(title.equals("") || title == null){
                return context.resources.getString(R.string.no_title)
            }else{
                return title
            }
        }

        fun getSafeOverview(context: Context): String {
            if(overview.equals("") || overview == null){
                return context.resources.getString(R.string.no_overview)
            }else{
                return overview
            }
        }

        fun getSafeScore(context: Context): String {
            if(vote_average.equals("") || vote_average == null){
                return context.resources.getString(R.string.no_vote_average)
            }else{
                return vote_average
            }
        }

    }


data class MovieResponse (
    val results: List<Movie>

)

