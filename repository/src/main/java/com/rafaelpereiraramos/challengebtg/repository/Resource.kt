package com.rafaelpereiraramos.challengebtg.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafaelpereiraramos.challengebtg.commonsandroid.ConnectivityHelper
import com.rafaelpereiraramos.challengebtg.repository.api.NetworkState

@Suppress("DataClassPrivateConstructor")
data class Resource<T> private constructor(
    private val _data: MutableLiveData<T?> = MutableLiveData(),
    private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()
) {

    val data: LiveData<T?> = _data
    val networkState: LiveData<NetworkState> = _networkState

    internal fun postData(value: T?) {
        _data. postValue(value)
    }

    internal fun postNetworkState(networkState: NetworkState) {
        _networkState.postValue(networkState)
    }

    companion object{
        fun <T> getEmptyResource() = Resource<T>()
    }
}