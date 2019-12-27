package com.example.moviedb.data

import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.response.GenreResponse
import com.example.moviedb.data.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDBGenres {
        @GET("genre/movie/list/")
        fun getGenres(
            @Query("api_key") apiKey :String = "c65150bf5825be7478ddb11d081c804f",
            @Query("language") language : String = "pt-BR"

        ) : Call <GenreResponse>

}