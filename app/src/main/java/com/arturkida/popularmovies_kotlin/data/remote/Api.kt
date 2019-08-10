package com.arturkida.popularmovies_kotlin.data.remote

import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.model.ResultGenres
import com.arturkida.popularmovies_kotlin.model.ResultMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET(BuildConfig.GENRES)
    fun getGenres(@Query("api_key") api_key: String) : Call<ResultGenres>

    @GET(BuildConfig.POPULAR_MOVIES)
    fun getPopularMovies(@Query("api_key") api_key: String) : Call<ResultMovies>
}