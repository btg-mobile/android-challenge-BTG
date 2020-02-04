package br.com.themoviebtg.endpoint

import br.com.themoviebtg.movies.model.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRest {

    @GET("movie/popular{apiKey}")
    fun getPopularMovies(@Path("apiKey") apiKey: String): Call<List<MovieModel>>
}