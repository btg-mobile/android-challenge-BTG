package com.example.desafiobtg.data.remote

interface RemoteDataSource {
    fun getMovieList(page: Int, success: (response: PopularMoviesResponse) -> Unit, failure: (error: String?) -> Unit)
}