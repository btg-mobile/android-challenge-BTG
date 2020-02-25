package br.eloibrito.com.movie_db.ui.filmes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.eloibrito.com.movie_db.data.repositorios.RepositoriosMovies

class ListMovieModelFactory(private val repositorio : RepositoriosMovies) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListMovieModel(repositorio) as T
    }
}