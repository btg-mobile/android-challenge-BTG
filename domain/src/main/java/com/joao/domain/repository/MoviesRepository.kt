package com.joao.domain.repository

import com.joao.domain.entity.Genre
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.MoviesItem
import com.joao.domain.entity.Response
import java.util.*

interface MoviesRepository {
    suspend fun getMovies(): Response<MoviesEntity>
    suspend fun getGenres(): Response<ArrayList<Genre>>
    suspend fun saveFavorite(movie: MoviesItem): Boolean
    suspend fun getFavoritesMovies(): MoviesEntity
}