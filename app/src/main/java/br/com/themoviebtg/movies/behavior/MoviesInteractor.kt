package br.com.themoviebtg.movies.behavior

import br.com.themoviebtg.endpoint.ApiClient
import br.com.themoviebtg.movies.model.MovieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesInteractor {
    interface FetchMoviesByGenreListener {
        fun onMovieByGenreFetchedSuccess(movieModelList: List<MovieModel>)
        fun onMovieByGenreFetchedFail(errorMessage: String)
    }

    fun fetchMoviesByGenre(genre: String, listener: FetchMoviesByGenreListener) {
        val apiKey = "1fa145a71f8cb2fd8f164fdd5b096df1"


        val getPopularMoviesCall = ApiClient.instance.getPopularMovies(apiKey)
        getPopularMoviesCall.enqueue(object : Callback<List<MovieModel>> {
            override fun onFailure(call: Call<List<MovieModel>>, t: Throwable) {
                listener.onMovieByGenreFetchedFail(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<List<MovieModel>>,
                response: Response<List<MovieModel>>
            ) {
                when (response.code()) {
                    200 -> response.body()?.let { listener.onMovieByGenreFetchedSuccess(it) }
                    404 -> listener.onMovieByGenreFetchedFail("Not found")
                }


            }

        })
    }
}