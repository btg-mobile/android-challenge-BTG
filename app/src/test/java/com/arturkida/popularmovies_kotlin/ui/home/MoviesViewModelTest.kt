package com.arturkida.popularmovies_kotlin.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import com.arturkida.popularmovies_kotlin.BaseUnitTest
import com.arturkida.popularmovies_kotlin.data.local.AppDatabase
import com.arturkida.popularmovies_kotlin.data.local.MovieDao
import com.arturkida.popularmovies_kotlin.data.local.MovieRepository
import com.arturkida.popularmovies_kotlin.utils.SearchType
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MoviesViewModelTest : BaseUnitTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieRepository: MovieRepository
    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var context: Context
    private lateinit var moviesViewModel: MoviesViewModel

    @Before
    fun setup() {
        // Given
        movieRepository = mock(MovieRepository::class.java)
        appDatabase = mock(AppDatabase::class.java)
        movieDao = mock(MovieDao::class.java)
        context = mock(Context::class.java)

        moviesViewModel = spy(MoviesViewModel(context))

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `must show movies list when a list is received`() {
        // Given
        val testList = getSingleMovieList()

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

    @Test
    fun `must filter movie list by title and return an empty list`() {
        // Given
        val testMovie = getMovieListWithAvengersAndLionKing()

        // When
        val testResult = moviesViewModel.searchMovies("Aladdin", SearchType.TITLE, testMovie)

        // Then
        assertThat(testResult.size, equalTo(0))
    }
}