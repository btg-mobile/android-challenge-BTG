package com.example.moviedb.data

import com.example.moviedb.BuildConfig.API_KEY
import com.example.moviedb.data.response.GenreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBGenres {
        @GET("genre/movie/list")
        fun getGenres(
            @Query("api_key") apiKey :String = API_KEY,
            @Query("language") language : String = "pt-BR"

        ) : Call <GenreResponse>

}