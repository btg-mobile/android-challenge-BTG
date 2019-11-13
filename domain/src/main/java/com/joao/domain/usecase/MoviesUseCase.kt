package com.joao.domain.usecase

import com.joao.domain.entity.Genre
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.MoviesItem
import com.joao.domain.entity.Response
import java.util.*

interface MoviesUseCase {
    suspend fun getMovies(): Response<MoviesEntity>
    suspend fun getFavoritesMovies(): MoviesEntity
    suspend fun getGenres(): Response<ArrayList<Genre>>
    suspend fun saveFavorite(movie:MoviesItem): Boolean
}