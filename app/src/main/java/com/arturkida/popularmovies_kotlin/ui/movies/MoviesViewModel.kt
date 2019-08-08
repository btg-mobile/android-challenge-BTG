package com.arturkida.popularmovies_kotlin.ui.movies

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.data.ApiImpl
import com.arturkida.popularmovies_kotlin.data.ApiResponse
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants

class MoviesViewModel : ViewModel() {

    val genres = MutableLiveData<List<Genre>>()
    val popularMovies = MutableLiveData<List<Movie>>()
//    val filteredMovies = mutableListOf<Movie>()
    var filteredMovies = mutableListOf<Movie>()

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

    fun searchMovies(search: String): MutableList<Movie> {
        Log.i(Constants.LOG_INFO, "Entering movie search")

        popularMovies.value?.let { movies ->
            if (movies.isNotEmpty()) {
                filteredMovies = movies.filter { movie ->
                    movie.title.toLowerCase().contains(search.toLowerCase())
                } as MutableList<Movie>
            }
        }
        Log.i(Constants.LOG_INFO, "Movies in filter: ${filteredMovies.size}")

        return filteredMovies
    }
}
