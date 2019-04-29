package com.example.desafiobtg.api

import com.example.desafiobtg.BuildConfig
import com.example.desafiobtg.data.remote.GenresResponse
import com.example.desafiobtg.data.remote.PopularMoviesResponse
import com.example.desafiobtg.db.entities.Genre
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int,
                         @Query("language") language: String? = null,
                         @Query("region") region: String? = null,
                         @Query("api_key") key: String = BuildConfig.theMovieDBKey) : Call<PopularMoviesResponse>

    @GET("genre/movie/list")
    fun getGenreList(@Query("language") language: String? = null,
                      @Query("api_key") key: String = BuildConfig.theMovieDBKey) : Call<GenresResponse>
}