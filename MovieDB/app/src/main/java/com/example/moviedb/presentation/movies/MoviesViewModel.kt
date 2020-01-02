package com.example.moviedb.presentation.movies

import androidx.lifecycle.ViewModel
import com.example.moviedb.data.Api
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.response.GenreResponse
import com.example.moviedb.data.response.MovieResponse
import com.example.moviedb.presentation.repository.DataRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    var hashGenre: HashMap<Int, String> = HashMap()

    fun getMovies(page: Int) {

        getGenres()

        Api.service.getMovies(page).enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {}

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies: MutableList<Movie> = mutableListOf()

                    response.body()?.let { movieResponse ->

                        for (movie in movieResponse.movieResults) {
                            val mov = Movie(
                                id = movie.id,
                                title = movie.title,
                                releaseDate = movie.releaseDate,
                                overview = movie.overview,
                                voteAverage = movie.voteAverage,
                                posterPath = movie.posterPath,
                                genres = movie.genres.map { "" + hashGenre[it] }

                            )
                            movies.add(mov)
                        }

                    }
                    DataRepository.setMoviesData(movies)
                }
            }

        })
    }

    private fun getGenres() {
        Api.genre.getGenres().enqueue(object : Callback<GenreResponse> {
            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {}

            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { genreResponse ->

                        for (genre in genreResponse.genreResults)
                            hashGenre[genre.id] = genre.title

                    }

                }
            }

        })
    }

}