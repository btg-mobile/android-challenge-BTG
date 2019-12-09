package com.brunoalmeida.movies.presentation.genders

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunoalmeida.movies.data.APIService
import com.brunoalmeida.movies.data.model.Genre
import com.brunoalmeida.movies.data.model.Movie
import com.brunoalmeida.movies.data.response.GenreResponse
import com.brunoalmeida.movies.data.response.PayloadGenreResponse
import com.brunoalmeida.movies.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GendersViewModel : ViewModel() {

    val genresLiveData: MutableLiveData<List<Genre>> = MutableLiveData()


    fun getGenders() {
        APIService.service.getGenres().enqueue(object : Callback<PayloadGenreResponse> {
            override fun onResponse(
                call: Call<PayloadGenreResponse>,
                response: Response<PayloadGenreResponse>
            ) {
                response.body()?.let {
                    for(genre in it.genres) {
                        MainActivity.database?.genreDao()?.insertGenres(Genre(genre.id, genre.name))
                    }
                }
            }

            override fun onFailure(call: Call<PayloadGenreResponse>, t: Throwable) {
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getGenreById(ids: List<String>) {
        val genresAux: MutableList<Genre> = mutableListOf()

        ids.iterator().forEach {
            val optGenre = MainActivity.database?.genreDao()?.findGenreById(it.toInt())
            if (optGenre!!.isPresent) {
                genresAux.add(optGenre.get())
            }
        }
        genresLiveData.value = genresAux
    }

}