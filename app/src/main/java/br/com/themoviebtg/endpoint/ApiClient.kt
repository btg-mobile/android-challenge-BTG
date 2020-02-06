package br.com.themoviebtg.endpoint

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {

        private const val theMovieDbApiVersion = 3
        @JvmStatic
        private val BASE_URL: String = "https://api.themoviedb.org/$theMovieDbApiVersion/"

        @JvmStatic
        private val retrofit: Retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @JvmStatic
        val instance: ApiRest = retrofit.create(ApiRest::class.java)
    }
}