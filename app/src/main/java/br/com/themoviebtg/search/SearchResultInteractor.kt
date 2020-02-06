package br.com.themoviebtg.search

import br.com.themoviebtg.BuildConfig
import br.com.themoviebtg.endpoint.ApiClient
import br.com.themoviebtg.movies.model.MovieModel
import br.com.themoviebtg.movies.model.MoviePaginationModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultInteractor {
    fun fetchMoviesByQuery(query: String, listener: OnMovieFetchedListener) {
        listener.onFetchedByQuerySuccess(listOf())
        val apiKey = BuildConfig.API_KEY

        val searchMovieCall = ApiClient.instance.searchMovie(apiKey, query)
        searchMovieCall.enqueue(object : Callback<MoviePaginationModel> {
            override fun onFailure(call: Call<MoviePaginationModel>, t: Throwable) {
                listener.onFetchedByQueryFail(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<MoviePaginationModel>,
                response: Response<MoviePaginationModel>
            ) {
                when (response.code()) {
                    200 -> response.body()?.let { listener.onFetchedByQuerySuccess(it.results) }
                    else -> listener.onFetchedByQueryFail("Error ${response.code()}")
                }
            }

        })
    }

    interface OnMovieFetchedListener {
        fun onFetchedByQuerySuccess(movieModelList: List<MovieModel>)
        fun onFetchedByQueryFail(errorMessage: String)

    }

}
