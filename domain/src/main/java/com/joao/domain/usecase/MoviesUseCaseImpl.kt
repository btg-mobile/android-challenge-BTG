package com.joao.domain.usecase

import com.joao.domain.entity.*
import com.joao.domain.repository.MoviesRepository
import java.util.*

class MoviesUseCaseImpl(private val moviesRepository: MoviesRepository) : MoviesUseCase {

    override suspend fun getMovies(): Response<MoviesEntity> {
        return moviesRepository.getMovies()
    }

    override suspend fun getFavoritesMovies(): MoviesEntity {
        return moviesRepository.getFavoritesMovies()
    }

    override suspend fun getGenres(): Response<ArrayList<Genre>> {
        return moviesRepository.getGenres()
    }

    override suspend fun saveFavorite(movie: MoviesItem): Boolean{
            return moviesRepository.saveFavorite(movie)
    }
}