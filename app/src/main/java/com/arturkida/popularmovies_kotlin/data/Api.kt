package com.arturkida.popularmovies_kotlin.data

import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.ResultGenres
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(BuildConfig.GENRES)
    fun getGenres(@Query("api_key") api_key: String) : Call<ResultGenres>
}