package com.arturkida.popularmovies_kotlin.ui.movies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arturkida.popularmovies_kotlin.data.ApiImpl
import com.arturkida.popularmovies_kotlin.data.ApiResponse
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie

class MoviesViewModel : ViewModel() {

    val genres = MutableLiveData<List<Genre>>()
    val popularMovies = MutableLiveData<List<Movie>>()

    fun getPopularMovies() {
        if (genres.value.isNullOrEmpty()) {
            getGenres()
        }

        ApiImpl().getPopularMovies(object: ApiResponse<List<Movie>> {
            override fun sucess(result: List<Movie>) {
                popularMovies.postValue(result)
            }

            override fun failure(error: Throwable?) {
                // TODO Implement on failure
            }
        })
    }

    private fun getGenres() {
        ApiImpl().getGenres(object: ApiResponse<List<Genre>> {
            override fun sucess(result: List<Genre>) {
                genres.postValue(result)
            }

            override fun failure(error: Throwable?) {
                // TODO Implement on failure
            }
        })
    }
}
