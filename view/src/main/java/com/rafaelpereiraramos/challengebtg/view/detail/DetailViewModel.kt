package com.rafaelpereiraramos.challengebtg.view.detail

import androidx.lifecycle.*
import com.rafaelpereiraramos.challengebtg.repository.AppRepository

class DetailViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    private val id = MutableLiveData<Int>()
    private val movieResult = id.map { appRepository.getMovieDetails(it, viewModelScope) }

    val movie = movieResult.switchMap { it.data }
    val networkState = movieResult.switchMap { it.networkState }
    val genres = movie.switchMap {
        appRepository.getGenres(it?.id!!)
    }

    fun getMovieDetails(id: Int) {
        this.id.value = id
    }

    fun changeFavouriteState(id: Int, state: Boolean) {
        appRepository.updateFavourite(id, state, viewModelScope)
    }
}