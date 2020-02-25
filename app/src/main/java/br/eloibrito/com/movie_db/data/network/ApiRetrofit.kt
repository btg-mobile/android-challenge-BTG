package br.eloibrito.com.movie_db.data.network

import br.eloibrito.com.movie_db.models.DadosJson
import io.reactivex.Observable
import retrofit2.http.*

interface ApiRetrofit {

    @GET("/3/genre/movie/list")
    fun get_generos(@Query("api_key") api_key: String): Observable<DadosJson>

    @GET("/3/discover/movie")
    fun get_movies(@Query("with_genres") generos: String, @Query("api_key") api_key: String): Observable<DadosJson>

    @GET("/3/movie/now_playing")
    fun get_movies_page(@Query("with_genres") generos: String, @Query("page") page: Int, @Query("api_key") api_key: String): Observable<DadosJson>
}