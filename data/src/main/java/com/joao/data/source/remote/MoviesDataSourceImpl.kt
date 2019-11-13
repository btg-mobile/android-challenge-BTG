package com.joao.data.source.remote

import android.content.Context
import com.joao.data.client.formatResponse
import com.joao.data.client.isNetworkConnected
import com.joao.data.mapper.GenresResponseToListGenres
import com.joao.data.mapper.MoviesResponseToMoviesMapper
import com.joao.data.source.remote.service.MoviesService
import com.joao.data.util.Constants
import com.joao.domain.entity.*

class MoviesDataSourceImpl(
    private val context: Context,
    private val moviesService: MoviesService
) : MoviesDataSource {
    override suspend fun getMovies(): Response<MoviesEntity> {
        if (!context.isNetworkConnected()) return ErrorResponse(
            RequestError(
                1,
                "Connection failed"
            )
        )
        return moviesService.getMovies(Constants.API_KEY).await()
            .formatResponse(MoviesResponseToMoviesMapper())
    }

    override suspend fun getGenres(): Response<ArrayList<Genre>> {
        if (!context.isNetworkConnected()) return ErrorResponse(
            RequestError(
                1,
                "Connection failed"
            )
        )
        return moviesService.getGenres(Constants.API_KEY).await()
            .formatResponse(GenresResponseToListGenres())
    }

}