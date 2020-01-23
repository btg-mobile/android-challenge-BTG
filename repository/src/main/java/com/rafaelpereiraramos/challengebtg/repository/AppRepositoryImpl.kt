package com.rafaelpereiraramos.challengebtg.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.switchMap
import androidx.paging.toLiveData
import com.rafaelpereiraramos.challengebtg.commonsandroid.ConnectivityHelper
import com.rafaelpereiraramos.challengebtg.repository.api.TmdbService
import com.rafaelpereiraramos.challengebtg.repository.db.dao.GenreDao
import com.rafaelpereiraramos.challengebtg.repository.db.dao.MovieDao
import com.rafaelpereiraramos.challengebtg.repository.db.dao.MovieGenreDao
import com.rafaelpereiraramos.challengebtg.repository.model.Genre
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.model.MovieGenre
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource
import com.rafaelpereiraramos.challengebtg.repository.paging.PopularMovieDataSourceFactory
import kotlinx.coroutines.CoroutineScope
import retrofit2.Response

class AppRepositoryImpl(
    private val connectivityHelper: ConnectivityHelper,
    private val service: TmdbService,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val movieGenreDao: MovieGenreDao
) : AppRepository {
    override fun getPopularMovies(scope: CoroutineScope): ListingResource<Movie> {
        val movieFactory = PopularMovieDataSourceFactory(connectivityHelper, service, scope)

        val pagedLiveData = movieFactory.toLiveData(pageSize = 30)

        return ListingResource(
            paged = pagedLiveData,
            networkState = movieFactory.dataSource.switchMap { it.networkState }
        )
    }

    override fun getMovieDetails(id: Int, scope: CoroutineScope): Resource<Movie?> =
        object : FetchRepositoryStrategy<Movie, Movie>(connectivityHelper, scope) {
            override fun loadFromDB(): LiveData<Movie?> = movieDao.getMovie(id)

            override fun shouldFetch(data: Movie?): Boolean = data == null

            override suspend fun createCall(): Response<Movie> = service.getMovieDetails(id)

            override fun processResponse(response: Response<Movie>): Movie? = response.body()

            override fun saveCallResult(item: Movie) {
                item.genres.forEach { genreDao.createGenre(it);  movieGenreDao.createMovieGenre(MovieGenre(item.id, it.id))}
                movieDao.createMovie(item)
            }

        }.resource

    override fun getGenres(movieId: Int): LiveData<List<Genre>> {
        return movieDao.getAllMovieGenre(movieId).switchMap {
            genreDao.getGenres(it.map { genre -> genre.genreId.toString() })
        }
    }
}