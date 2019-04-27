package com.example.desafiobtg.api

import com.example.desafiobtg.BuildConfig
import com.example.desafiobtg.data.remote.PopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/movie/popular")
    fun getPopularMovies(@Query("page") page: Int,
                         @Query("language") language: String,
                         @Query("region") region: String,
                         @Query("api_key") key: String = BuildConfig.theMovieDBKey) : Call<PopularMoviesResponse>
}