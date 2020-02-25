package br.eloibrito.com.movie_db.ui.favoritos

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.eloibrito.com.movie_db.R
import br.eloibrito.com.movie_db.models.Movies
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMoviesFavoritos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler

class ListMovieFavoritosModel(private val repositorio: RepositoriosMoviesFavoritos) : ViewModel() {

    val _isLoading = MutableLiveData<Boolean>()
    val _isMessageError = MutableLiveData<String>()
    val _listaFavoritos = MutableLiveData<List<Movies>>()


    init {
        _isLoading.postValue(false)
        _listaFavoritos.value = arrayListOf()
    }


    @SuppressLint("CheckResult")
    fun pegar_favoritos(context: Context) {
        ativa_desativa_loading(true)

        val movies = repositorio.getFavoritos()

        movies.subscribeOn(IoScheduler()).subscribeOn(AndroidSchedulers.mainThread())
            .doFinally { ativa_desativa_loading(false) }
            .subscribe(
                {
                    if (!it.isNullOrEmpty()) {

                        _listaFavoritos.postValue(it)
                    }

                    ativa_desativa_loading(false)
                },
                { _isMessageError.postValue(context.resources.getString(R.string.erro_filmes_favoritos)) }
            )
    }

    fun ativa_desativa_loading(status: Boolean) = _isLoading.postValue(status)
}