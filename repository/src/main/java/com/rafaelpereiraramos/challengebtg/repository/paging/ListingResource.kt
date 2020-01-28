package com.rafaelpereiraramos.challengebtg.repository.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.rafaelpereiraramos.challengebtg.repository.api.NetworkState

class ListingResource<T>(
    val paged: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>
)