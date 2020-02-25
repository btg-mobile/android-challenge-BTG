package br.eloibrito.com.movie_db.data.repositorios

import br.eloibrito.com.movie_db.data.dao.AppDataBase
import br.eloibrito.com.movie_db.models.DadosJson
import br.eloibrito.com.movie_db.data.network.ApiRetrofit
import br.eloibrito.com.movie_db.models.Movies
import io.reactivex.Flowable
import io.reactivex.Observable

class RepositoriosMoviesFavoritos(private val db: AppDataBase) {

    fun getFavoritos(): Flowable<List<Movies>> = db.getFavDAO().getAllMovies()
}