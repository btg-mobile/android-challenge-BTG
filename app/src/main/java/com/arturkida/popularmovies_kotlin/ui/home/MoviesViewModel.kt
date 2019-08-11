package com.arturkida.popularmovies_kotlin.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.arturkida.popularmovies_kotlin.data.local.MovieRepository
import com.arturkida.popularmovies_kotlin.data.remote.ApiImpl
import com.arturkida.popularmovies_kotlin.data.remote.ApiResponse
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants
import com.arturkida.popularmovies_kotlin.utils.SearchType

class MoviesViewModel(context: Context) : ViewModel() {

    val genres = MutableLiveData<List<Genre>>()
    val popularMovies: MutableLiveData<List<Movie>>? = MutableLiveData()
    var filteredMovies = mutableListOf<Movie>()

    private val repository = MovieRepository(context)
    val favoriteMovies = repository.allFavoriteMovies

    fun mustShowMoviesList(moviesList: List<Movie>) = moviesList.isNotEmpty()

    fun populateGenresNameFrom(movie: Movie): Movie {

        movie.genre_names = ""
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

        return movie
    }

    fun updateFavoriteStatusOf(movie: Movie): Movie {
        movie.favorite = isMovieFavorited(movie)

        return movie
    }

    private fun isMovieFavorited(movie: Movie): Boolean {
        favoriteMovies?.value?.let { favoritesList ->
            favoritesList.forEach { favoriteMovie ->
                if (favoriteMovie.id == movie.id) {
                    return true
                }
            }
        }

        return false
    }

    fun getPopularMovies() {
        if (genres.value.isNullOrEmpty()) {
            getGenres()
        }

        ApiImpl()
            .getPopularMovies(object: ApiResponse<List<Movie>> {
            override fun onSuccess(result: List<Movie>) {
                postPopularMoviesResult(result)
            }

            override fun onFailure(error: Throwable?) {
                postPopularMoviesResult(listOf())
            }
        })
    }

    fun postPopularMoviesResult(result: List<Movie>) {
        popularMovies?.postValue(result)
    }

    fun getGenres() {
        ApiImpl()
            .getGenres(object: ApiResponse<List<Genre>> {
                override fun onSuccess(result: List<Genre>) {
                    genres.postValue(result)
                }

                override fun onFailure(error: Throwable?) {
                    genres.postValue(listOf())
                }
            })
    }

    fun searchMovies(searchText: String, searchType: SearchType, searchList: List<Movie>): MutableList<Movie> {

        when(searchType) {
            SearchType.TITLE ->
                if (searchList.isNotEmpty()) {
                    filteredMovies = searchList.filter { movie ->
                        movie.title.toLowerCase().contains(searchText.toLowerCase())
                    } as MutableList<Movie>
                }
            SearchType.YEAR ->
                if (searchList.isNotEmpty()) {
                    filteredMovies = searchList.filter { movie ->
                        movie.release_date.contains(searchText)
                    } as MutableList<Movie>
                }
        }

        return filteredMovies
    }
}
