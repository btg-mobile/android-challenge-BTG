package com.arturkida.popularmovies_kotlin.data.remote

interface ApiResponse<T> {
    fun onSuccess(result: T)
    fun onFailure(error: Throwable?)
}