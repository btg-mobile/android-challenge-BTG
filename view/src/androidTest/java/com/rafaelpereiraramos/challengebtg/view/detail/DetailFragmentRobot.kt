package com.rafaelpereiraramos.challengebtg.view.detail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.ViewTestApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

internal fun arrange(func: DetailFragmentArrange.() -> Unit) = DetailFragmentArrange().apply(func)
internal fun act(func: DetailFragmentAct.() -> Unit) = DetailFragmentAct().apply(func)
internal fun assert(func: DetailFragmentAssert.() -> Unit) = DetailFragmentAssert().apply { func(); stopKoin()}

class DetailFragmentArrange {

    fun mockViewModel(viewModel: DetailViewModel) {
        val application = ApplicationProvider.getApplicationContext<ViewTestApplication>()
        startKoin {
            androidContext(application)
        }
        loadKoinModules(listOf(module {
            viewModel { viewModel }
        }))
    }

    fun launch() {
        launchFragmentInContainer<DetailFragment>(fragmentArgs = Bundle().apply { putInt("id", -1) }, themeResId = R.style.AppTheme)
    }
}

class DetailFragmentAct {

}

class DetailFragmentAssert {

    fun checkIfFavouriteButtonLabelIsToRemove() {
        onView(withText(R.string.detail_fragment_remove_favourites))
            .check(matches(isDisplayed()))
    }

    fun checkIfFavouriteButtonLabelIsToAdd() {
        onView(withText(R.string.detail_fragment_add_favourites))
            .check(matches(isDisplayed()))
    }
}