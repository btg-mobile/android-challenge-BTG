package com.arturkida.popularmovies_kotlin.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import com.arturkida.popularmovies_kotlin.BaseUnitTest
import com.arturkida.popularmovies_kotlin.data.local.AppDatabase
import com.arturkida.popularmovies_kotlin.data.local.MovieDao
import com.arturkida.popularmovies_kotlin.data.local.MovieRepository
import com.arturkida.popularmovies_kotlin.model.Genre
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

@RunWith(JUnit4::class)
class MoviesViewModelTest : BaseUnitTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var context: Context

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

        whenever(movieDao.getAllMovies()).thenReturn(getSimpleLiveDataMovieList())

        moviesViewModel = spy(MoviesViewModel(context))
    }

    @Test
    fun `must show movies list when a list is received`() {
        // Given
        val testList = getSimpleMovieList()

        // When
        val testResult = moviesViewModel.mustShowMoviesList(testList)

        // Then
        assertThat(testResult, equalTo(true))
    }
}