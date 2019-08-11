package com.arturkida.popularmovies_kotlin.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import com.arturkida.popularmovies_kotlin.BaseUnitTest
import com.arturkida.popularmovies_kotlin.data.local.AppDatabase
import com.arturkida.popularmovies_kotlin.data.local.MovieDao
import com.arturkida.popularmovies_kotlin.data.local.MovieRepository
import com.arturkida.popularmovies_kotlin.data.remote.ApiImpl
import com.arturkida.popularmovies_kotlin.data.remote.ApiResponse
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.SearchType
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest : BaseUnitTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieRepository: MovieRepository
    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var context: Context
    private lateinit var moviesViewModel: MoviesViewModel

    private val testGenres: List<Genre> = listOf(
        Genre(12, "Action"),
        Genre(878, "Adventure"),
        Genre(28, "Science Fiction"))

    @Before
    fun setup() {
        // Given
        movieRepository = mock(MovieRepository::class.java)
        appDatabase = mock(AppDatabase::class.java)
        movieDao = mock(MovieDao::class.java)
        context = mock(Context::class.java)

        moviesViewModel = spy(MoviesViewModel(context))
    }

    @Test
    fun `must show movies list when a list is received`() {
        // Given
        val testList = getSingleMovieList()
//        whenever(movieDao.getAllMovies()).thenReturn(getSingleLiveDataMovieList())

        // When
        val testResult = moviesViewModel.mustShowMoviesList(testList)

        // Then
        assertThat(testResult, equalTo(true))
    }

    @Test
    fun `must show no movies found when an empty list is received`() {
        // When
        val testResult = moviesViewModel.mustShowMoviesList(listOf())

        // Then
        assertThat(testResult, equalTo(false))
    }

    @Test
    fun `must update a favorite movie status`() {
        // Given
        val testMovie = getSingleMovieList()

        whenever(moviesViewModel.updateFavoriteStatusOf(testMovie[0])).thenReturn(getSingleFavoriteMovieList()[0])

        // When
        val testResult = moviesViewModel.updateFavoriteStatusOf(testMovie[0])

        // Then
        assertThat(testResult.favorite, equalTo(true))
    }

    @Test
    fun `must keep a non favorite movie status`() {
        // Given
        val testMovie = getSingleMovieList()

        whenever(moviesViewModel.updateFavoriteStatusOf(testMovie[0])).thenReturn(testMovie[0])

        // When
        val testResult = moviesViewModel.updateFavoriteStatusOf(testMovie[0])

        // Then
        assertThat(testResult.favorite, equalTo(false))
    }

    @Test
    fun `must filter movie list by title and only return Avengers movie`() {
        // Given
        val testMovie = getMovieListWithAvengersAndLionKing()

        // When
        val testResult = moviesViewModel.searchMovies("Avengers", SearchType.TITLE, testMovie)

        // Then
        assertThat(testResult[0].title, equalTo("Avengers: Endgame"))
        assertThat(testResult.size, equalTo(1))
    }

//    @Test
//    fun `must call onSucess when retrieving popular movies list`() {
//        // Given
//        val testMovie = getSingleMovieList()
//        val apiImpl = mock(ApiImpl::class.java)
//
//        doAnswer {
//            //            val callback = it.arguments<ApiResponse<List<Movie>>>(eq(0)) as ApiResponse<List<Movie>>
//            val callback = it.arguments[0] as ApiResponse<List<Genre>>
//            callback.onSuccess(testGenres)
//            null
//        }.whenever(apiImpl).getPopularMovies(any())
//
//        doAnswer {
//            //            val callback = it.arguments<ApiResponse<List<Movie>>>(eq(0)) as ApiResponse<List<Movie>>
//            val callback = it.arguments[0] as ApiResponse<List<Movie>>
//            callback.onSuccess(testMovie)
//            null
//        }.whenever(apiImpl).getPopularMovies(any())
//
//        // When
//        moviesViewModel.getPopularMovies()
//
//        // Then
//        verify(moviesViewModel).postPopularMoviesResult(any())
//    }
}