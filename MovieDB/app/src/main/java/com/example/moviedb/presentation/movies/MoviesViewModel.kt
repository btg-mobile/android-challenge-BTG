package com.example.moviedb.presentation.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedb.data.Api
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.response.GenreResponse
import com.example.moviedb.data.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MoviesViewModel : ViewModel() {

    val moviesLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    val moviesData: MutableLiveData<List<Movie>> = MutableLiveData()

    var hash: HashMap<String, String> = HashMap()

    fun getMovies() {
        Api.service.getMovies().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("getMovies   ", t.message)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies: MutableList<Movie> = mutableListOf()

                    response.body()?.let { movieResponse ->

                        for (movie in movieResponse.movieResults) {
                            val mov = Movie(
                                id = movie.id,
                                title = movie.title,
                                year = movie.releaseDate,
                                overview = movie.overview,
                                voteAverage = movie.voteAverage,
                                posterPath = movie.posterPath

                            )
                            movies.add(mov)
                        }

                    }
                    moviesLiveData.value = movies
                    moviesData.value = movies
                }
            }

        })
    }


    fun getGenres() {
        Api.genre.getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {


                    response.body()?.let { genreResponse ->

                        for (genre in genreResponse.genreResults) {
                            hash.put(
                                genre.id,
                                genre.title
                            )

                        }

                    }

                }
            }

        })
    }


    fun searchMovieByTitle(title  : String)  {
        val movies: MutableList<Movie> = mutableListOf()

        for (movie in moviesLiveData.value!!) {
            if(movie.title.toLowerCase().contains(title))
                movies.add(movie)
        }

        moviesData.value = movies

    }

    fun toggleFavoriteMovie(id : Int) : Boolean{
        val movies = moviesData.value

            movies?.find {
                e ->
                val ide = e.id
                ide== id


        }

//        moviesLiveData.value?.find { it.id == id }?.favorite = bool

        return false
    }
}