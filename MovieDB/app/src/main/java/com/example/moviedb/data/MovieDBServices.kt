package com.example.moviedb.data

import com.example.moviedb.BuildConfig.API_KEY
import com.example.moviedb.data.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBServices {
        @GET("movie/popular/")
        fun getMovies(
            @Query("api_key") apiKey :String = API_KEY,
            @Query("language") language : String = "pt-BR",
            @Query("page") page : String = "",
            @Query("region") region : String = ""

        ) : Call <MovieResponse>
}