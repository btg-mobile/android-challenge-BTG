package com.arturkida.popularmovies_kotlin.ui.popular.ui.movies

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.arturkida.popularmovies_kotlin.data.ApiImpl
import com.arturkida.popularmovies_kotlin.data.ApiResponse
import com.arturkida.popularmovies_kotlin.model.Genre

class MoviesViewModel : ViewModel() {

    fun getGenres() {
        ApiImpl().getGenres(object: ApiResponse<List<Genre>> {
            override fun sucess(result: List<Genre>) {
                Log.i("API", "Retornou na ViewModel Sucesso")
            }

            override fun failure(error: Throwable?) {
                Log.i("API", "Retornou na ViewModel Falha")
            }
        })
    }
}
