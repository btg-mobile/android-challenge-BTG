package com.rafaelpereiraramos.challengebtg.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.rafaelpereiraramos.challengebtg.commonsandroid.ConnectivityHelper
import com.rafaelpereiraramos.challengebtg.repository.api.TmdbService
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import kotlinx.coroutines.CoroutineScope

class PopularMovieDataSourceFactory(
    private val connectivityHelper: ConnectivityHelper,
    private val service: TmdbService,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {

    val dataSource = MutableLiveData<PopularMoviePagedDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source = PopularMoviePagedDataSource(connectivityHelper, service, scope)

        dataSource.postValue(source)

        return source
    }
}