package com.example.leopaim.themoviedb.main

import com.example.leopaim.themoviedb.fragment.MainFragment
import com.example.leopaim.themoviedb.model.GenreResponse
import com.example.leopaim.themoviedb.model.MovieResponse
import com.example.leopaim.themoviedb.network.ApiRepository
import com.example.leopaim.themoviedb.network.MDApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainFragment, private val apiRepository: ApiRepository, private val gson:Gson){
    fun getMovieList(page: String){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(MDApi.getPopularMovies(page)),
                MovieResponse::class.java)
            uiThread {
                if(page.equals("1")){
                    view.showMovieList(data.results)
                }else{
                    view.showMoreMovieList(data.results)
                }
            }
        }
    }

    fun getGenreList(){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(MDApi.getGenres()),
                GenreResponse::class.java)
            uiThread {
                view.showGenreList(data.genres)
            }
        }
    }

    fun searchMovie(query: String, page: String){
        doAsync {
            val data = gson.fromJson(apiRepository.doRequest(MDApi.searchMovie(query, page)),
                MovieResponse::class.java)
            uiThread {
                view.showMovieList(data.results)
            }
        }
    }

}