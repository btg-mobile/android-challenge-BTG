package com.rafaelpereiraramos.challengebtg.view.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.rafaelpereiraramos.challengebtg.repository.AppRepository
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource

class SearchViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _query = MutableLiveData<String>()

    private val popularResponse = MutableLiveData<ListingResource<Movie>>()
    val pagedMovies: LiveData<PagedList<Movie>> = popularResponse.switchMap {
        it.paged
    }

    private val _favourites = MediatorLiveData<List<Movie>>()
    val favourites = _favourites as LiveData<List<Movie>>

    private val _filterFavourites = MutableLiveData<String>()
    val filterFavourites = _filterFavourites as LiveData<String>

    fun loadPopularMovies() {
        popularResponse.value = repository.getPopularMovies(viewModelScope)
    }

    fun refreshFavourites() {
        val favourites = repository.getFavourites()
        _favourites.addSource(favourites) {
            _favourites.removeSource(favourites)
            _favourites.value = it
        }
    }

    fun search(query: String, currentPage: Fragment) {
        if (currentPage is SearchResultFragment) {
            _query.value = query
        } else {
            _filterFavourites.value = query
        }
    }
}