package com.uchoa.btg.movie.network

import android.support.annotation.NonNull
import com.uchoa.btg.movie.callbacks.OnGetGenresCallback
import com.uchoa.btg.movie.callbacks.OnGetMovieCallback
import com.uchoa.btg.movie.callbacks.OnGetMoviesCallback
import com.uchoa.btg.movie.callbacks.OnGetTrailersCallback
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.models.api.GenresResponse
import com.uchoa.btg.movie.models.api.MoviesResponse
import com.uchoa.btg.movie.models.api.TrailerResponse
import com.uchoa.btg.movie.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesRepository private constructor(private val moviesServiceApi: MoviesServiceApi) {

    private var language: String = Constants.DEFAULT_LANGUAGE

    fun getMovies(page: Int, callback: OnGetMoviesCallback) {
        moviesServiceApi.getPopularMovies(
            Constants.MOVIES_API_KEY, language, page
        )
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    @NonNull call: Call<MoviesResponse>,
                    @NonNull response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val moviesResponse = response.body()
                        if (moviesResponse?.movies != null) {
                            callback.onSuccess(moviesResponse.page, moviesResponse.movies!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    fun getMovie(movieId: Int, callback: OnGetMovieCallback) {
        moviesServiceApi.getMovie(movieId, Constants.MOVIES_API_KEY, language)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful) {
                        val movie = response.body()
                        if (movie != null) {
                            callback.onSuccess(movie)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    fun getGenres(callback: OnGetGenresCallback) {
        moviesServiceApi.getGenres(Constants.MOVIES_API_KEY, language)
            .enqueue(object : Callback<GenresResponse> {
                override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                    if (response.isSuccessful) {
                        val genresResponse = response.body()
                        if (genresResponse?.genres != null) {
                            callback.onSuccess(genresResponse.genres!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    fun getTrailers(movieId: Int, callback: OnGetTrailersCallback) {

        moviesServiceApi.getTrailers(movieId, Constants.MOVIES_API_KEY, language)
            .enqueue(object : Callback<TrailerResponse> {
                override fun onResponse(call: Call<TrailerResponse>, response: Response<TrailerResponse>) {
                    if (response.isSuccessful) {
                        val trailerResponse = response.body()

                        if (trailerResponse?.trailers != null) {
                            callback.onSuccess(trailerResponse.trailers!!)
                        } else {
                            callback.onError()
                        }
                    } else {
                        callback.onError()
                    }
                }

                override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
                    callback.onError()
                }
            })
    }

    fun setUpLanguage(lang: String) {
        language = lang
    }

    companion object {

        private var repository: MoviesRepository? = null

        val instance: MoviesRepository
            get() {
                if (repository == null) {
                    val retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    repository = MoviesRepository(retrofit.create(MoviesServiceApi::class.java))
                }

                return repository as MoviesRepository
            }
    }
}