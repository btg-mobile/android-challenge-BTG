package com.arturkida.popularmovies_kotlin.data

interface ApiResponse<T> {
    fun sucess(result: T)
    fun failure(error: Throwable?)
}