package com.joao.data.repository

import com.joao.data.source.local.FavoritesDataSource
import com.joao.data.source.remote.MoviesDataSource
import com.joao.domain.entity.Genre
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.MoviesItem
import com.joao.domain.entity.Response
import com.joao.domain.repository.MoviesRepository
import java.util.*

class MoviesRepositoryImpl(
    private val moviesDataSource: MoviesDataSource,
    private val favoritesDataSource: FavoritesDataSource
) : MoviesRepository {
    override suspend fun getFavoritesMovies(): MoviesEntity {
        return favoritesDataSource.getFavorites()
    }

    override suspend fun saveFavorite(movie: MoviesItem): Boolean {
        return favoritesDataSource.saveFavorite(movie)
    }

    override suspend fun getMovies(): Response<MoviesEntity> {
        return moviesDataSource.getMovies()
    }

    override suspend fun getGenres(): Response<ArrayList<Genre>> {
        return moviesDataSource.getGenres()
    }

}