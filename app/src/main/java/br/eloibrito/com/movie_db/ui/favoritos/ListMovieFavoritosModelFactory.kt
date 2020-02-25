package br.eloibrito.com.movie_db.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMoviesFavoritos

class ListMovieFavoritosModelFactory(private val repositorio : RepositoriosMoviesFavoritos) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListMovieFavoritosModel(
            repositorio
        ) as T
    }
}