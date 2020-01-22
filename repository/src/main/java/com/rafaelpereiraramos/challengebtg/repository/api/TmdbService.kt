package com.rafaelpereiraramos.challengebtg.repository.api

import com.rafaelpereiraramos.challengebtg.repository.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "pt-BR"
    ): Response<PopularSearchResponse>
}