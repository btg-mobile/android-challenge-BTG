package com.uchoa.btg.movie.network

import com.uchoa.btg.movie.models.api.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.uchoa.btg.movie.models.Movie
import retrofit2.http.Path
import com.uchoa.btg.movie.models.api.GenresResponse
import com.uchoa.btg.movie.models.api.TrailerResponse

interface MoviesServiceApi {

    @GET("movie/popular")
    fun getPopularMovies(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKEy: String,
        @Query("language") language: String
    ): Call<Movie>

    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<GenresResponse>

    @GET("movie/{movie_id}/videos")
    fun getTrailers(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKEy: String,
        @Query("language") language: String
    ): Call<TrailerResponse>
}