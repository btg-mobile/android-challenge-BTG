package com.arturkida.popularmovies_kotlin.data.local

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class GenresTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToGenresList(genreList: String): List<Int> {
        if (genreList.isNullOrBlank()) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Int>>() {}.type

        return gson.fromJson(genreList, listType)
    }

    @TypeConverter
    fun genresListToString(genreList: List<Int>): String {
        return gson.toJson(genreList)
    }
}