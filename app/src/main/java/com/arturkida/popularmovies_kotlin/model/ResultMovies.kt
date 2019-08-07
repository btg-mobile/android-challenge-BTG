package com.arturkida.popularmovies_kotlin.model

import android.os.Parcel
import android.os.Parcelable

data class ResultMovies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createTypedArrayList(Movie) as List<Movie>,
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeTypedList(results)
        parcel.writeInt(total_pages)
        parcel.writeInt(total_results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultMovies> {
        override fun createFromParcel(parcel: Parcel): ResultMovies {
            return ResultMovies(parcel)
        }

        override fun newArray(size: Int): Array<ResultMovies?> {
            return arrayOfNulls(size)
        }
    }
}