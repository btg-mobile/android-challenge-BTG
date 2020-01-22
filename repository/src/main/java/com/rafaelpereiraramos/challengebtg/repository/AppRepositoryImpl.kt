package com.rafaelpereiraramos.challengebtg.repository

import androidx.lifecycle.switchMap
import androidx.paging.toLiveData
import com.rafaelpereiraramos.challengebtg.commonsandroid.ConnectivityHelper
import com.rafaelpereiraramos.challengebtg.repository.api.TmdbService
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource
import com.rafaelpereiraramos.challengebtg.repository.paging.PopularMovieDataSourceFactory
import kotlinx.coroutines.CoroutineScope

class AppRepositoryImpl(
    private val connectivityHelper: ConnectivityHelper,
    private val service: TmdbService
) : AppRepository {

    override fun getPopularMovies(scope: CoroutineScope): ListingResource<Movie> {
        val repoFactory = PopularMovieDataSourceFactory(connectivityHelper, service, scope)

        val pagedLiveData = repoFactory.toLiveData(pageSize = 30)

        return ListingResource(
            paged = pagedLiveData,
            networkState = repoFactory.dataSource.switchMap { it.networkState }
        )
    }
}