package com.joao.data.client

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.joao.data.mapper.Mapper
import com.joao.data.util.Constants
import com.joao.domain.entity.ErrorResponse
import com.joao.domain.entity.RequestError
import com.joao.domain.entity.SuccessResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    inline fun <reified T> makeService(): T {
        return OkHttpClient.Builder().apply {
        }.build().let { retrofitCreate(it) }
    }


    inline fun <reified T> retrofitCreate(okHttpClient: OkHttpClient): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(T::class.java)
    }
}

fun <T, O> Response<T>.formatResponse(mapper: Mapper<T, O>): com.joao.domain.entity.Response<O> {
    return if (this.isSuccessful) {
        if (this.body() != null)
            SuccessResponse(mapper.transform(this.body()))
        else
            ErrorResponse(RequestError(this.code(), this.message()))
    } else {
        val error = this.errorBody()
        val jsonObject = JsonParser().parse(error?.string()).asJsonObject
        val obj = Gson().fromJson<RequestError>(jsonObject, RequestError::class.java)
        if (obj is RequestError)
            ErrorResponse(obj)
        else
            ErrorResponse(RequestError(10, "Erro"))
        //TODO ALTERAR DEPOIS
    }
}