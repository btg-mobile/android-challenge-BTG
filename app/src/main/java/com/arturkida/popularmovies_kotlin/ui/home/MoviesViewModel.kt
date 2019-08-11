package com.arturkida.popularmovies_kotlin.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.arturkida.popularmovies_kotlin.data.remote.ApiImpl
import com.arturkida.popularmovies_kotlin.data.remote.ApiResponse
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants
import com.arturkida.popularmovies_kotlin.utils.SearchType

class MoviesViewModel : ViewModel() {

    val genres = MutableLiveData<List<Genre>>()
    val popularMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies = MutableLiveData<List<Movie>>()
    var filteredMovies = mutableListOf<Movie>()

    fun populateGenresName(movie: Movie): Movie {

        movie.genre_names = ""

        if (!genres.value.isNullOrEmpty()) {
            genres.value?.let {genresList ->
                genresList.forEach {genre ->
                    if (movie.genre_ids.contains(genre.id)) {
                        if (movie.genre_names == "") {
                            movie.genre_names = genre.name
                        } else {
                            movie.genre_names += ", ${genre.name}"
                        }
                    }
                }
            }
        }

        return movie
    }

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

    fun getFavoriteMovies() {
        if (genres.value.isNullOrEmpty()) {
            getGenres()
        }

        ApiImpl()
            .getPopularMovies(object: ApiResponse<List<Movie>> {
                override fun sucess(result: List<Movie>) {
                    favoriteMovies.postValue(result)
                }

                override fun failure(error: Throwable?) {
                    // TODO Implement on failure
                }
            })
    }

    private fun getGenres() {
        ApiImpl()
            .getGenres(object: ApiResponse<List<Genre>> {
                override fun sucess(result: List<Genre>) {
                    genres.postValue(result)
                }

                override fun failure(error: Throwable?) {
                    // TODO Implement on failure
                }
            })
    }

    fun searchMovies(searchText: String, searchType: SearchType, searchList: MutableLiveData<List<Movie>>): MutableList<Movie> {
        Log.i(Constants.LOG_INFO, "Entering movie search")

        when(searchType) {
            SearchType.TITLE -> searchList.value?.let { movies ->
                if (movies.isNotEmpty()) {
                    filteredMovies = movies.filter { movie ->
                        movie.title.toLowerCase().contains(searchText.toLowerCase())
                    } as MutableList<Movie>
                }
            }
            SearchType.YEAR -> searchList.value?.let { movies ->
                if (movies.isNotEmpty()) {
                    filteredMovies = movies.filter { movie ->
                        movie.release_date.contains(searchText)
                    } as MutableList<Movie>
                }
            }
        }
        Log.i(Constants.LOG_INFO, "Movies in filter: ${filteredMovies.size}")

        return filteredMovies
    }
}
