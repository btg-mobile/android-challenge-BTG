package com.arturkida.popularmovies_kotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.not

open class BaseRobot {
    protected fun isDisplayed(resId: Int): ViewInteraction = onView(withId(resId)).check(matches(isDisplayed()))

    protected fun isNotDisplayed(resId: Int): ViewInteraction = onView(withId(resId)).check(matches(not(isDisplayed())))

    protected fun clickOnTab(text: String): ViewInteraction = onView((withText(text))).perform(click())

    protected fun matchText(resId: Int, text: String): ViewInteraction = onView(withId(resId)).check(matches(withText(text)))

    protected fun matchHint(resId: Int, text: String): ViewInteraction = onView(withId(resId)).check(matches(withHint(text)))

    protected fun fillEditText(resId: Int, text: String): ViewInteraction = onView(withId(resId)).perform(replaceText(text))
}