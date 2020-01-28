package com.rafaelpereiraramos.challengebtg.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rafaelpereiraramos.challengebtg.repository.model.Movie
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    private lateinit var viewModel: DetailViewModel

    @Test fun whenLoadMovieFavouriteStateTrueShouldButtonTextBeIsToRemove() {
        val movie = MutableLiveData<Movie>()
        arrange {
            viewModel = mockkClass(DetailViewModel::class, relaxed = true)
            every { viewModel.movie } returns movie
            mockViewModel(viewModel)
            launch()
        }

        act {
            movie.postValue(Movie(
                id = 0,
                favourite = true
            ))
        }

        assert {
            checkIfFavouriteButtonLabelIsToRemove()
        }
    }

    @Test fun whenLoadMovieFavouriteStateFalseShouldButtonTextBeIsToAdd() {
        val movie = MutableLiveData<Movie>()
        arrange {
            viewModel = mockkClass(DetailViewModel::class, relaxed = true)
            every { viewModel.movie } returns movie
            mockViewModel(viewModel)
            launch()
        }

        act {
            movie.postValue(Movie(
                id = 0,
                favourite = false
            ))
        }

        assert {
            checkIfFavouriteButtonLabelIsToAdd()
        }
    }
}