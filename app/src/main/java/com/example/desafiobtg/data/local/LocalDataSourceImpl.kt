package com.example.desafiobtg.data.local

import android.arch.lifecycle.LiveData
import com.example.desafiobtg.AppController
import com.example.desafiobtg.db.entities.FavoriteMovie
import com.example.desafiobtg.db.entities.Genre
import com.example.desafiobtg.db.entities.Movie
import com.example.desafiobtg.db.room.Database
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(): LocalDataSource {

    @Inject
    lateinit var db: Database

    override fun insertMovies(movieList: List<Movie>) {
        AppController.runOnBG { db.movieModel().insertAll(movieList) }
    }

    override fun clearMovieTable() {
        AppController.runOnBG {
            val movieIdList = getFavoriteIds()
            db.movieModel().deleteAllExcept(movieIdList)
        }
    }

    override fun getMovieById(movieId: String?, success: (result: Movie?) -> Unit) {
        AppController.runOnBG {
            val movie = db.movieModel().getMovieById(movieId)
            AppController.runOnMain { success(movie) }
        }
    }

    override fun addGenres(genres: List<Genre>) {
        AppController.runOnBG { db.genreModel().insertAll(genres) }
    }

    override fun getLocalMovieList(page: Int) = db.movieModel().getMoviesForPage(page)
    override fun getGenreByIds(genreIds: List<Int>) = db.genreModel().getGenreForIds(genreIds)

    override fun addFavoriteMovie(movieId: String) {
        AppController.runOnBG { db.favoriteMovieModel().insert(FavoriteMovie(movieId)) }
    }

    override fun removeFavoriteMovie(movieId: String) {
        AppController.runOnBG { db.favoriteMovieModel().delete(FavoriteMovie(movieId)) }
    }

    override fun getFavoriteMovies(): LiveData<List<Movie>?> = db.favoriteMovieModel().getFavoriteMovies()

    override fun getFavoriteIdsLiveData(): LiveData<List<String>> = db.favoriteMovieModel().getFavoriteIdsLiveData()

    override fun getFavoriteIds(): List<String> = db.favoriteMovieModel().getFavoriteIds()
}