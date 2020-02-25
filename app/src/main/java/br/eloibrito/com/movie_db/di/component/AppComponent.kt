package br.eloibrito.com.movie_db.di.component

import br.eloibrito.com.movie_db.di.module.ModuloApp
import br.eloibrito.com.movie_db.di.module.RetrofitModulo
import br.eloibrito.com.movie_db.di.module.RoomModulo
import br.eloibrito.com.movie_db.ui.*
import br.eloibrito.com.movie_db.ui.favoritos.FragmentoFavoritos
import br.eloibrito.com.movie_db.ui.filmes.FragmentoFilmes
import br.eloibrito.com.movie_db.utils.App
import dagger.Component

@Component(modules = [ModuloApp::class, RetrofitModulo::class, RoomModulo::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(movies: FragmentoFilmes)
    fun inject(movies: FragmentoFavoritos)
    fun inject(movies: ListMoviesActivityTeste)
    fun inject(movies: ListMoviesDetailsActivity)
}