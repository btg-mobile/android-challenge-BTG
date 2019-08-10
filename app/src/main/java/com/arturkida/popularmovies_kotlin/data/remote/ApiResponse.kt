package com.arturkida.popularmovies_kotlin.data.remote

interface ApiResponse<T> {
    fun sucess(result: T)
    fun failure(error: Throwable?)
}