package com.example.moviedb.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Api {

    private fun initRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
    val service : MovieDBServices = initRetrofit().create(MovieDBServices::class.java)

    val genre : MovieDBGenres = initRetrofit().create(MovieDBGenres::class.java)
}