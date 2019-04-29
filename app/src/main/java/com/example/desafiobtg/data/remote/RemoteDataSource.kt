package com.example.desafiobtg.data.remote

import com.example.desafiobtg.db.entities.Genre

interface RemoteDataSource {
    fun getMovieList(page: Int, success: (response: PopularMoviesResponse) -> Unit, failure: (error: String?) -> Unit)
    fun getGenreList(success: (response: List<Genre>) -> Unit, failure: (error: String?) -> Unit)
}