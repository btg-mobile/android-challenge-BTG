package com.rafaelpereiraramos.challengebtg.repository

import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource
import kotlinx.coroutines.CoroutineScope

interface AppRepository {

    fun getPopularMovies(scope: CoroutineScope): ListingResource<Movie>

    fun getMovieDetails(id: Int, scope: CoroutineScope): Resource<Movie>
}