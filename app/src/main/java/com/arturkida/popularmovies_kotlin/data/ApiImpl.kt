package com.arturkida.popularmovies_kotlin.data

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.ResultGenres
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiImpl {

    val genresLiveDataResponse: MutableLiveData<ResultGenres> = MutableLiveData()

    companion object Factory {
        fun create(): Api {

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(Api::class.java)
        }
    }

//    fun init() {
//        Retrofit.Builder()
//            .baseUrl(BuildConfig.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    fun getGenres() : MutableLiveData<ResultGenres> {
        val call = create().genres(BuildConfig.MOVIEDB_API_KEY)
        Log.i("API", "Chamando API de Gênero")
        call.enqueue(object : Callback<ResultGenres?> {
            override fun onFailure(call: Call<ResultGenres?>?, t: Throwable?) {
                Log.i("API", "Deu errado")
            }
            override fun onResponse(call: Call<ResultGenres?>?, response: Response<ResultGenres?>?) {
                response?.body()?.let {
//                    genresLiveDataResponse.value = it
                    Log.i("API", "Deu certo")
                }
            }
        })

        return genresLiveDataResponse
    }

}