package br.com.themoviebtg.endpoint

import br.com.themoviebtg.movies.model.MoviePaginationModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRest {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MoviePaginationModel>
}