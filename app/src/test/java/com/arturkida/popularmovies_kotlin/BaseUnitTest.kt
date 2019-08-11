package com.arturkida.popularmovies_kotlin

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.arturkida.popularmovies_kotlin.model.Movie

open class BaseUnitTest {

    private val testGenreIds: List<Int> = listOf(12, 878, 28)

    fun getSimpleLiveDataMovieList(): LiveData<List<Movie>> {
        val liveData = MutableLiveData<List<Movie>>()
        liveData.postValue(getSimpleMovieList())

        return liveData
    }

    fun getSimpleMovieList(): List<Movie> {
        return listOf(Movie(
            id=299534,
            adult=false,
            backdrop_path="/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg",
            genre_ids=testGenreIds,
            original_language="en",
            original_title="Avengers: Endgame",
            overview="After the devastating events of Avengers: Infinity War, the universe is in ruins due to the efforts of the Mad Titan, Thanos. With the help of remaining allies, the Avengers must assemble once more in order to undo Thanos' actions and restore order to the universe once and for all, no matter what consequences may be in store.",
            popularity=164.315,
            poster_path="/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
            release_date="2019-04-24",
            title="Avengers: Endgame",
            video=false,
            vote_average=8.4,
            vote_count=8588,
            favorite=false,
            genre_names="Action, Adventure, Science Fiction"))
    }
}