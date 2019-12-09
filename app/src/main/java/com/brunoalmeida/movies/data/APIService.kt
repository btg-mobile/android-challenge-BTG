package com.brunoalmeida.movies.data

import com.brunoalmeida.movies.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIService {

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val service: MDBServices = initRetrofit().create(MDBServices::class.java)

}