package com.joao.data.source.remote.service

import com.joao.data.model.GenresResponse
import com.joao.data.model.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Deferred<Response<MoviesResponse>>

    @GET("/3/genre/movie/list")
    fun getGenres(@Query("api_key") apiKey: String): Deferred<Response<GenresResponse>>

}