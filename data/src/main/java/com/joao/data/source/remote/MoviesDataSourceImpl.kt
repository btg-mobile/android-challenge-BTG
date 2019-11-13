package com.joao.data.source.remote

import com.joao.data.client.formatResponse
import com.joao.data.mapper.GenresResponseToListGenres
import com.joao.data.mapper.MoviesResponseToMoviesMapper
import com.joao.data.source.remote.service.MoviesService
import com.joao.data.util.Constants
import com.joao.domain.entity.Genre
import com.joao.domain.entity.MoviesEntity
import com.joao.domain.entity.Response

class MoviesDataSourceImpl(
    private val moviesService: MoviesService
) : MoviesDataSource {
    override suspend fun getMovies(): Response<MoviesEntity> {
        return moviesService.getMovies(Constants.API_KEY).await()
            .formatResponse(MoviesResponseToMoviesMapper())
    }

    override suspend fun getGenres(): Response<ArrayList<Genre>> {
        return moviesService.getGenres(Constants.API_KEY).await()
            .formatResponse(GenresResponseToListGenres())
    }

}