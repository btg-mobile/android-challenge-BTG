package com.rafaelpereiraramos.challengebtg.repository.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.rafaelpereiraramos.challengebtg.commonsandroid.ConnectivityHelper
import com.rafaelpereiraramos.challengebtg.repository.api.NetworkState
import com.rafaelpereiraramos.challengebtg.repository.api.TmdbService
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularMoviePagedDataSource(
    private val connectivityHelper: ConnectivityHelper,
    private val service: TmdbService,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Movie>() {

    val networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        if (connectivityHelper.hasNetwork()) {
            scope.launch(Dispatchers.IO) {
                networkState.postValue(NetworkState.LOADING)

                val response = service.getPopularMovies()

                if (response.isSuccessful) {
                    val popularMovies = response.body()?.results
                    callback.onResult(popularMovies!!.toMutableList(), null, 2)
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    networkState.postValue(NetworkState.error(response.message()))
                }
            }
        } else {
            networkState.postValue(NetworkState.NO_CONNECTION)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        if (connectivityHelper.hasNetwork()) {
            scope.launch {
                networkState.postValue(NetworkState.LOADING)

                val response = service.getPopularMovies(
                    page = params.key
                )

                if (response.isSuccessful) {
                    val popularMovies = response.body()?.results
                    callback.onResult(
                        popularMovies!!.toMutableList(),
                        params.key.inc()
                    )
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    networkState.postValue(NetworkState.error(response.message()))
                }
            }
        } else {
            networkState.postValue(NetworkState.NO_CONNECTION)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        //only loads forward
    }
}