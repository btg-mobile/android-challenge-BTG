package com.curymorais.twitchtop100

import android.util.Log
import com.curymorais.moviedbapp.BuildConfig
import com.curymorais.moviedbapp.Contract
import com.curymorais.twitchtop100.data.domain.TopMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesInteractor : Contract.BaseInteractor {

    val API_KEY = BuildConfig.API_KEY

    override fun getMovieArrayList(onFinishedListener: Contract.BaseInteractor.OnFinishedListener) {
        var call = RetrofitInitializer().movieService().getPopularMovies(API_KEY,"en-US","1")

        call.enqueue(object: Callback<TopMovies> {
            override fun onResponse(call: Call<TopMovies>, response: Response<TopMovies>) {
                onFinishedListener.onSuccess(response.body()?.results)
            }

            override fun onFailure(call: Call<TopMovies>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}