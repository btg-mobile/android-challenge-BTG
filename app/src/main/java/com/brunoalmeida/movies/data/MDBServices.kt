package com.brunoalmeida.movies.data

import com.brunoalmeida.movies.data.response.GenreResponse
import com.brunoalmeida.movies.BuildConfig.API_KEY
import com.brunoalmeida.movies.data.response.PayloadGenreResponse
import com.brunoalmeida.movies.data.response.PayloadResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MDBServices {

    @GET("""movie/popular$API_KEY&language=pt-BR""")
    fun getMovies(@Query("page") page: String): Call<PayloadResponse>

    @GET("""search/movie$API_KEY&language=pt-BR""")
    fun getMoviesSearch(@Query("page") page: String, @Query("query") key: String): Call<PayloadResponse>

    @GET("""genre/movie/list$API_KEY&language=pt-BR""")
    fun getGenres(): Call<PayloadGenreResponse>

}