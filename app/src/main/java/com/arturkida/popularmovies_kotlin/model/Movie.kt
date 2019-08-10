package com.arturkida.popularmovies_kotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import com.arturkida.popularmovies_kotlin.data.local.GenresTypeConverter

@Suppress("UNCHECKED_CAST")
@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    @TypeConverters(GenresTypeConverter::class)
    val genre_ids: List<Int>,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var favorite: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readArrayList(Int::class.java.classLoader) as List<Int>,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(backdrop_path)
        parcel.writeList(genre_ids)
        parcel.writeString(original_language)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(poster_path)
        parcel.writeString(release_date)
        parcel.writeString(title)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeDouble(vote_average)
        parcel.writeInt(vote_count)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}