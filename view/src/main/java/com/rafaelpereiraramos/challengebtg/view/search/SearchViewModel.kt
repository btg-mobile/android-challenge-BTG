package com.rafaelpereiraramos.challengebtg.view.search

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.rafaelpereiraramos.challengebtg.repository.AppRepository
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource

class SearchViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val popularResponse = MutableLiveData<ListingResource<Movie>>()
    val pagedMovies: LiveData<PagedList<Movie>> = popularResponse.switchMap {
        it.paged
    }

    fun loadPopularMovies() {
        popularResponse.value = repository.getPopularMovies(viewModelScope)
    }
}