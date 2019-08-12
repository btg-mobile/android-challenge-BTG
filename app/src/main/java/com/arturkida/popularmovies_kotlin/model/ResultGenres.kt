package com.arturkida.popularmovies_kotlin.model

import android.os.Parcel
import android.os.Parcelable

data class ResultGenres(
    val genres: List<Genre>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Genre) as List<Genre>)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(genres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultGenres> {
        override fun createFromParcel(parcel: Parcel): ResultGenres {
            return ResultGenres(parcel)
        }

        override fun newArray(size: Int): Array<ResultGenres?> {
            return arrayOfNulls(size)
        }
    }
}