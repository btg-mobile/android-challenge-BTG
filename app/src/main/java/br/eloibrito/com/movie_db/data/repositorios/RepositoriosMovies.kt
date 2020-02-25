package br.eloibrito.com.movie_db.data.repositorios

import br.eloibrito.com.movie_db.data.dao.AppDataBase
import br.eloibrito.com.movie_db.models.DadosJson
import br.eloibrito.com.movie_db.data.network.ApiRetrofit
import br.eloibrito.com.movie_db.models.Movies
import io.reactivex.Observable

class RepositoriosMovies(private val api: ApiRetrofit, private val db: AppDataBase) {

    fun getGeneros(api_key: String): Observable<DadosJson> = api.get_generos(api_key)
    fun getMovies(generos: String, page: Int, api_key: String): Observable<DadosJson> =
        api.get_movies_page(generos, page, api_key)

    fun checkFavoritos(idMovie: Long): Boolean = db.getFavDAO().getMovies(idMovie).isNotEmpty()
    fun likeDeslike(movies: Movies, status: Boolean) = if(!status) db.getFavDAO().deletaMovies(movies.id!!) else db.getFavDAO().insertMovies(movies)
}