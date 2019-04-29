package com.example.desafiobtg.data.remote

import com.example.desafiobtg.api.MovieApi
import com.example.desafiobtg.db.entities.Genre
import com.example.desafiobtg.db.entities.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(): RemoteDataSource {

    @Inject
    lateinit var api: MovieApi

    override fun getMovieList(page: Int, success: (response: PopularMoviesResponse) -> Unit, failure: (error: String?) -> Unit) {
        val defaultLocale = Locale.getDefault()
        val language = defaultLocale.toString().replace('_', '-')
        val region = defaultLocale.country
        val call = api.getPopularMovies(page, language, region)

        call.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onResponse(call: Call<PopularMoviesResponse>, response: Response<PopularMoviesResponse>) {
                response.body()?.let { movies ->
                    success(movies)
                } ?: failure("Null response body")
            }

            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                failure(t.message)
            }

        })
    }

    override fun getGenreList(success: (response: List<Genre>) -> Unit, failure: (error: String?) -> Unit) {
        val defaultLocale = Locale.getDefault()
        val language = defaultLocale.toString().replace('_', '-')
        val call = api.getGenreList(language)

        call.enqueue(object : Callback<GenresResponse> {
            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                response.body()?.let { genre ->
                    success(genre.genres)
                } ?: failure("Null response body")
            }

            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                failure(t.message)
            }

        })
    }
}

data class PopularMoviesResponse(@Expose var page: Int = 1,
                                 @Expose @SerializedName("results") var movieList: List<Movie> = emptyList())

data class GenresResponse(@Expose @SerializedName("genres") var genres: List<Genre>)