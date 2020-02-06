package br.com.themoviebtg.endpoint

import br.com.themoviebtg.authentication.TokenModel
import br.com.themoviebtg.movies.model.MoviePaginationModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRest {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<MoviePaginationModel>


    @GET("authentication/token/new")
    fun getNewToken(@Query("api_key") apiKey: String): Call<TokenModel>

    @GET("authenticate/{token}/allow")
    fun allowToken(
        @Path("token") token: String,
        @Query("api_key") apiKey: String
    ): Call<ResponseBody>


    @GET("search/movie")
    fun searchMovie(@Query("api_key") apiKey: String,
                    @Query("query") query: String): Call<MoviePaginationModel>


}