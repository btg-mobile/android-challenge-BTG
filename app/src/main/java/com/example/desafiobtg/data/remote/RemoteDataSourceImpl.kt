package com.example.desafiobtg.data.remote

import com.example.desafiobtg.api.MovieApi
import com.example.desafiobtg.db.entities.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(): RemoteDataSource {

    @Inject
    lateinit var api: MovieApi

    override fun getMovieList(page: Int, success: (response: PopularMoviesResponse) -> Unit, failure: (error: String?) -> Unit) {
        val call = api.getPopularMovies(page, "pt-BR", "BR")

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

}

data class PopularMoviesResponse(@Expose var page: Int = 1,
                                 @Expose @SerializedName("results") var movieList: List<Movie> = emptyList())