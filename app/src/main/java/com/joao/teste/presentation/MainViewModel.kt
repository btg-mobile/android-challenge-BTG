package com.joao.teste.presentation

import androidx.lifecycle.*
import com.joao.domain.entity.*
import com.joao.domain.usecase.MoviesUseCase
import com.joao.teste.presentation.base.DefaultViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val moviesUseCase: MoviesUseCase) : DefaultViewModel() {

    val loading: LiveData<Boolean> get() = _loading
    val updateList: LiveData<ArrayList<MoviesItem?>> get() = _updateList
    val updateListFavorites: LiveData<ArrayList<MoviesItem?>> get() = _updateListFavorites
    val error: LiveData<String> get() = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _updateList: MutableLiveData<ArrayList<MoviesItem?>> = MutableLiveData()
    private val _updateListFavorites: MutableLiveData<ArrayList<MoviesItem?>> = MutableLiveData()
    private val _error: MutableLiveData<String> = MutableLiveData()


    private val listMovies = ArrayList<MoviesItem?>()
    private val listFavoritesMovies = ArrayList<MoviesItem?>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun fetchMovies() {
        listMovies.clear()
        listFavoritesMovies.clear()
        uiScope.launch {
            _loading.postValue(true)

            val moviesEntity = moviesUseCase.getFavoritesMovies()
            listFavoritesMovies.addAll(moviesEntity.movies)
            _updateListFavorites.postValue(listFavoritesMovies)


            when (val value = moviesUseCase.getMovies()) {
                is SuccessResponse -> {
                    value.body.movies.forEach {
                        if (listFavoritesMovies.find { x -> x?.id == it?.id } != null)
                            it?.isFavorite = true
                        listMovies.add(it)
                    }
                    _updateList.postValue(listMovies)
                    _loading.postValue(false)
                }
                is ErrorResponse -> {
                    _loading.postValue(false)
                    _error.postValue(value.error.message)
                }
            }

        }
    }

}