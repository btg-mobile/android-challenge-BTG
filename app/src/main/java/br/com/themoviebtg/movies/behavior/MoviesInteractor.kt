package br.com.themoviebtg.movies.behavior

import br.com.themoviebtg.BuildConfig
import br.com.themoviebtg.endpoint.ApiClient
import br.com.themoviebtg.movies.model.MovieModel
import br.com.themoviebtg.movies.model.MoviePaginationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesInteractor {
    interface FetchMoviesByGenreListener {
        fun onMovieByGenreFetchedSuccess(movieModelList: List<MovieModel>)
        fun onMovieByGenreFetchedFail(errorMessage: String)
    }

    fun fetchPopularMovies(listener: FetchMoviesByGenreListener) {
        val apiKey = BuildConfig.API_KEY


        val getPopularMoviesCall = ApiClient.instance.getPopularMovies(apiKey)
        getPopularMoviesCall.enqueue(object : Callback<MoviePaginationModel> {
            override fun onFailure(call: Call<MoviePaginationModel>, t: Throwable) {
                listener.onMovieByGenreFetchedFail(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<MoviePaginationModel>,
                response: Response<MoviePaginationModel>
            ) {
                when (response.code()) {
                    200 -> response.body()?.let { listener.onMovieByGenreFetchedSuccess(it.results) }
                    else -> listener.onMovieByGenreFetchedFail("Error ${response.code()}")
                }


            }

        })
    }
}