package com.example.desafiobtg.data.remote

import com.example.desafiobtg.db.entities.Movie

class RemoteDataSourceImpl: RemoteDataSource {
}

data class PopularMoviesResponse(val page: Int, val movieList: List<Movie>)