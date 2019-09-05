package com.curymorais.twitchtop100

import com.curymorais.twitchtop100.data.domain.TopMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {

    @GET("3/movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey:String, @Query("language") language:String,@Query("page") page:String) : Call<TopMovies>

}