package br.eloibrito.com.movie_db.ui

import android.view.View
import br.eloibrito.com.movie_db.models.Generos
import br.eloibrito.com.movie_db.models.Movies

interface RecycleViewItemClick {

    fun onClickGeneros(view: View, generos: Generos, pos : Int)

    fun onClickMovies(view: View, movies: Movies, pos : Int)

    fun onClickMoviesLike(movies: Movies, pos : Int, like : Boolean)

    fun onClear()
}