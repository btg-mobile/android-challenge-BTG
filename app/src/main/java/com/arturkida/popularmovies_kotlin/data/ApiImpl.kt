package com.arturkida.popularmovies_kotlin.data

import android.util.Log
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.model.ResultGenres
import com.arturkida.popularmovies_kotlin.model.ResultMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiImpl {

    companion object {
        val retrofit: Api = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    fun getGenres(callback: ApiResponse<List<Genre>>) {
        val call = retrofit.getGenres(BuildConfig.MOVIEDB_API_KEY)
        Log.i("API", "Calling genres API")

        call.enqueue(object : Callback<ResultGenres?> {
            override fun onFailure(call: Call<ResultGenres?>?, t: Throwable?) {
                Log.e("API", t?.message)
                callback.failure(t)
            }
            override fun onResponse(call: Call<ResultGenres?>?, response: Response<ResultGenres?>?) {
                response?.body()?.let {
                    Log.i("API", "Genres successfully returned")
                    callback.sucess(it.genres)
                }
            }
        })
    }

    fun getPopularMovies(callback: ApiResponse<List<Movie>>) {
        val call = retrofit.getPopularMovies(BuildConfig.MOVIEDB_API_KEY)
        Log.i("API", "Calling movies API")

        call.enqueue(object : Callback<ResultMovies?> {
            override fun onFailure(call: Call<ResultMovies?>?, t: Throwable?) {
                Log.e("API", t?.message)
                callback.failure(t)
            }
            override fun onResponse(call: Call<ResultMovies?>?, response: Response<ResultMovies?>?) {
                response?.body()?.let {
                    Log.i("API", "Movies successfully returned")
                    callback.sucess(it.results)
                }
            }
        })
    }
}