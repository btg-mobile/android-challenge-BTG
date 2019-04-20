package com.example.leopaim.themoviedb.network

import com.example.leopaim.themoviedb.BuildConfig.API_KEY
import com.example.leopaim.themoviedb.BuildConfig.BASE_URL
import java.util.*

object MDApi {
//    TODO Uncomment to app translation together with STRING file
//    val language = Locale.getDefault().toString().replace("_","-");
    val language = "pt-BR"

    fun getPopularMovies(page: String): String {
        return BASE_URL +"movie/popular" +  API_KEY + "&language=" + language + "&page=0" + page;
    }

    fun getGenres(): String {
        return BASE_URL +"genre/movie/list" +  API_KEY + "&language=" + language;
    }

    fun searchMovie(query: String, page: String): String{
        return BASE_URL +"search/movie" +  API_KEY + "&language=" + language+ "&query=" + query+ "&page=0" + page + "&include_adult=false";
    }


}