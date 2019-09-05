package com.curymorais.twitchtop100

import com.curymorais.moviedbapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun movieService () = retrofit.create(MovieDbService::class.java)
}