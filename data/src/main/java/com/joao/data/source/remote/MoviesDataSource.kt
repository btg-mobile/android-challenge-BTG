package com.joao.data.source.remote

import com.joao.domain.entity.Genre
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.Response

interface MoviesDataSource {
    suspend fun getMovies(): Response<MoviesEntity>
    suspend fun getGenres(): Response<ArrayList<Genre>>
}