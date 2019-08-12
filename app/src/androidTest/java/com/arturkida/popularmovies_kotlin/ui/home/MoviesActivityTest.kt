package com.arturkida.popularmovies_kotlin.ui.home

import android.support.test.espresso.IdlingRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.idlingresource.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesActivityTest {

    @get:Rule
    val rule = ActivityTestRule<MoviesActivity>(MoviesActivity::class.java)

    fun robot(func: MoviesRobot.() -> Unit) = MoviesRobot()
        .apply { func() }

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregister() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun onHomeScreen_CheckIfTablayoutIsDisplayed() {
        robot {
            checkIfTabLayoutIsDisplayed(R.id.tab_layout)
        }
    }

    @Test
    fun onHomeScreen_CheckSearchHintFromPopularMoviesScreen() {
        robot {
            checkHintFromPopularSearchBar(R.id.et_search_popular_movies, "Search by movie here")
        }
    }

    @Test
    fun typeMovieTitleThatDoesNotExit_CheckIfEmptyListTextIsShown() {
        robot {
            typeTextInPopularSearchBar(R.id.et_search_popular_movies, "djkagfu3g54r4i")
            clickOnKeyboardSearchButton(R.id.et_search_popular_movies)
            checkIfEmptyListTextIsDisplayed(R.id.tv_empty_popular_list)
            checkIfMovieListTextIsNotDisplayed(R.id.rv_popular_movie_list)
        }
    }

    @Test
    fun clickOnFavoriteTab_MustShowFavoriteTab() {
        robot {
            clickOnFavoriteTab("Favorites")
            checkIfFavoriteScreenIsDisplayed(R.id.fragment_favorite_movies)
            checkIfPopularScreenIsNotDisplayed(R.id.fragment_popular_movies)
        }
    }
}