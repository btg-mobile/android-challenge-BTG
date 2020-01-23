package com.rafaelpereiraramos.challengebtg.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rafaelpereiraramos.challengebtg.commonsandroid.ConnectivityHelper
import com.rafaelpereiraramos.challengebtg.repository.api.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class FetchRepositoryStrategy<ResultType, RequestType>(
    private val connectivityHelper: ConnectivityHelper,
    private val scope: CoroutineScope
) {
    val resource = Resource.getEmptyResource<ResultType?>().apply {
        postNetworkState(NetworkState.LOADING)

        if(connectivityHelper.hasNetwork()) {
            val mediatorHelper = MediatorLiveData<ResultType>()
            val dbResult = loadFromDB()

            mediatorHelper.addSource(dbResult) {
                if (shouldFetch(it)) {
                    scope.launch {
                        val response = createCall()

                        if (response.isSuccessful) {
                            val resultType = processResponse(response)!!

                            saveCallResult(resultType)
                            postData(resultType)
                        }
                    }
                } else {
                    postData(it)
                    postNetworkState(NetworkState.LOADED)
                }
            }
        } else {
            postNetworkState(NetworkState.NO_CONNECTION)
        }
    }

    protected abstract fun loadFromDB(): LiveData<ResultType?>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Response<RequestType>

    protected abstract fun processResponse(response: Response<RequestType>): ResultType?

    protected abstract fun saveCallResult(item: ResultType)
}