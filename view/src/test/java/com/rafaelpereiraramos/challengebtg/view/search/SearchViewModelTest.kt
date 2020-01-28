package com.rafaelpereiraramos.challengebtg.view.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rafaelpereiraramos.challengebtg.repository.AppRepository
import com.rafaelpereiraramos.challengebtg.repository.api.NetworkState
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import com.rafaelpereiraramos.challengebtg.repository.paging.ListingResource
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {

    @Rule @JvmField var allSynchronousTask = InstantTaskExecutorRule()
    private lateinit var viewModel: SearchViewModel
    private lateinit var repository: AppRepository

    @Before fun init() {
        repository = mockkClass(AppRepository::class)
        viewModel = SearchViewModel(repository)
    }

    @Test fun `when search at unknown page should do nothing`() {
        // arrange

        // act
        viewModel.search("brasil", Fragment())

        // assert
        verify(exactly = 0) {repository.searchMovies(any(), any())}
        viewModel.filterFavourites.observeForever { Assert.fail() }
    }

    @Test fun `when search at search page should call repository`() {
        // arrange
        viewModel.movies.observeForever {  }
        every { repository.searchMovies(any(), any()) } returns ListingResource(MutableLiveData<PagedList<Movie>>(), MutableLiveData<NetworkState>())

        // act
        viewModel.search("brasil", SearchResultFragment())

        // assert
        verify(exactly = 1) { repository.searchMovies(any(), any()) }
    }

    @Test fun `when search at favourite page should update filter`() {
        // arrange

        // act
        viewModel.search("brasil", FavouritesFragment())

        // assert
        viewModel.filterFavourites.observeForever { Assert.assertEquals("brasil", it) }
    }

    @Test fun `when search empty string at search page should load popular movies`() {
        // arrange
        viewModel.movies.observeForever {  }
        every { repository.getPopularMovies(any()) } returns ListingResource(MutableLiveData<PagedList<Movie>>(), MutableLiveData<NetworkState>())

        // act
        viewModel.search("", SearchResultFragment())

        // assert
        verify(exactly = 1) { repository.getPopularMovies(any()) }
    }
}