package com.rafaelpereiraramos.challengebtg.repository

import androidx.lifecycle.LiveData
import com.rafaelpereiraramos.challengebtg.repository.model.Genre
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource
import kotlinx.coroutines.CoroutineScope

interface AppRepository {

    fun getPopularMovies(scope: CoroutineScope): ListingResource<Movie>

    fun getMovieDetails(id: Int, scope: CoroutineScope): Resource<Movie?>

    fun getGenres(movieId: Int): LiveData<List<Genre>>

    fun updateFavourite(id: Int, favourite: Boolean, scope: CoroutineScope)

    fun getFavourites(): LiveData<List<Movie>>

    fun searchMovies(query: String, scope: CoroutineScope): ListingResource<Movie>
}