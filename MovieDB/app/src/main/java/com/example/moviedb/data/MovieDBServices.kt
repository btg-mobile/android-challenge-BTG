package com.example.moviedb.data

import com.example.moviedb.BuildConfig.API_KEY
import com.example.moviedb.data.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBServices {
    @GET("movie/popular/")
    fun getMovies(
        @Query("page") page: Int = 1,
        @Query("region") region: String = "",
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "pt-BR"

    ): Call<MovieResponse>
}