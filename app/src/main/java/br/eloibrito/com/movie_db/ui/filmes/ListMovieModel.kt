package br.eloibrito.com.movie_db.ui.filmes

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.models.Generos
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.data.network.EndPoint
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMovies
import br.eloibrito.com.movie_db.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler

class ListMovieModel(private val repositorio: RepositoriosMovies) : ViewModel() {

    val _isLoading = MutableLiveData<Boolean>()
    val _isMessageError = MutableLiveData<String>()
    val _listaGeneros = MutableLiveData<List<Generos>>()
    val _listaMovies = MutableLiveData<List<Movies>>()
    val _page = MutableLiveData<Int>()


    init {
        _isLoading.postValue(false)
        _listaMovies.value = arrayListOf()
    }

    @SuppressLint("CheckResult")
    fun buscarGeneros(context: Context, page: Int) {

        val generos = repositorio.getGeneros(EndPoint.chave_api)

        generos.subscribeOn(IoScheduler()).subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (!it.lista_generos.isNullOrEmpty()) {
                        _listaGeneros.postValue(it.lista_generos)
                        val codGeneros = IntArray(0)
                        buscarFilmes(codGeneros, context, page)
                    }
                },
                { _isMessageError.postValue(context.resources.getString(R.string.erro_generos)) }
            )
    }

    @SuppressLint("CheckResult")
    fun buscarFilmes(generos: IntArray, context: Context, page: Int) {
        ativa_desativa_loading(true)

        val generos_implode = Utils.implodeString(generos, ",")

        val movies = repositorio.getMovies(generos_implode, page, EndPoint.chave_api)

        movies.subscribeOn(IoScheduler()).subscribeOn(AndroidSchedulers.mainThread())
            .doFinally { ativa_desativa_loading(false) }
            .subscribe(
                {
                    if (!it.lista_movies.isNullOrEmpty()) {
                        for (m in it.lista_movies!!)
                            m.like = repositorio.checkFavoritos(m.id!!)

                        _page.postValue(page)
                        _listaMovies.postValue(it.lista_movies)
                    }

                    ativa_desativa_loading(false)
                },
                { _isMessageError.postValue(context.resources.getString(R.string.erro_filmes)) }
            )
    }


    fun likeDeslike(movies: Movies, pos: Int, like: Boolean): Int {
        movies.like = like
        repositorio.likeDeslike(movies, like)

        return pos

    }

    fun ativa_desativa_loading(status: Boolean) = _isLoading.postValue(status)
}