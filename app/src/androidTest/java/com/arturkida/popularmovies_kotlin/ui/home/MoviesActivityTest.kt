package com.arturkida.popularmovies_kotlin.ui.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.arturkida.popularmovies_kotlin.R
import kotlinx.android.synthetic.main.fragment_movies.*
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MoviesActivityTest {

    @get:Rule
    val rule = ActivityTestRule<MoviesActivity>(MoviesActivity::class.java)

//    @Before
//    fun init() {
//        rule.activity.supportFragmentManager.beginTransaction()
//    }

    @Test
    fun onHomeScreen_CheckIfTablayoutIsDisplayed() {
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun onHomeScreen_CheckSearchHintFromPopularMoviesScreen() {
        onView(withId(R.id.et_search_popular_movies)).check(matches(withHint("Search by movie here")))
    }

    @Test
    fun typeMovieTitleThatDoesNotExit_CheckIfEmptyListTextIsShown() {
        onView(withId(R.id.et_search_popular_movies)).perform(typeText("djkagfu3g54r4i"))
        onView(withId(R.id.et_search_popular_movies)).perform(pressImeActionButton())

        onView(withId(R.id.tv_empty_popular_list)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_movie_list)).check(matches(not(isDisplayed())))
    }

    @Test
    fun clickOnFavoriteTab_MustShowFavoriteTab() {
        onView(withText("Favorites")).perform(click())
        onView(withId(R.id.fragment_favorite_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_popular_movies)).check(matches(not(isDisplayed())))
    }
}