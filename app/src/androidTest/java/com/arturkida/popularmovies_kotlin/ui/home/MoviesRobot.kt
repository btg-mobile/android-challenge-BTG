package com.arturkida.popularmovies_kotlin.ui.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.pressImeActionButton
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.arturkida.popularmovies_kotlin.BaseRobot

class MoviesRobot : BaseRobot() {
    fun checkIfTabLayoutIsDisplayed(resId: Int) {
        isDisplayed(resId)
    }

    fun checkHintFromPopularSearchBar(resId: Int, text: String) {
        matchHint(resId, text)
    }

    fun typeTextInPopularSearchBar(resId: Int, text: String) {
        fillEditText(resId, text)
    }

    fun clickOnKeyboardSearchButton(resId: Int) {
        onView((withId(resId))).perform(pressImeActionButton())
    }

    fun checkIfEmptyListTextIsDisplayed(resId: Int) {
        isDisplayed(resId)
    }

    fun checkIfEmptyListTextIsNotDisplayed(resId: Int) {
        isNotDisplayed(resId)
    }

    fun checkIfMovieListTextIsDisplayed(resId: Int) {
        isDisplayed(resId)
    }

    fun checkIfMovieListTextIsNotDisplayed(resId: Int) {
        isNotDisplayed(resId)
    }

    fun clickOnFavoriteTab(text: String) {
        clickOnTab(text)
    }

    fun checkIfFavoriteScreenIsDisplayed(resId: Int) {
        isDisplayed(resId)
    }

    fun checkIfPopularScreenIsNotDisplayed(resId: Int) {
        isNotDisplayed(resId)
    }
}