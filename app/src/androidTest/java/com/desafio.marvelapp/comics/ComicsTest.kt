package com.desafio.marvelapp.comics

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.desafio.marvelapp.characters.RoboComic
import com.desafio.marvelapp.initMockServer
import com.desafio.marvelapp.main.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicsTest {
    @Rule @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)
    private lateinit var robo: RoboComic
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.initMockServer()
        robo = RoboComic()
        activityRule.launchActivity(Intent())
    }

    @Test
    @Throws(Exception::class)
    fun search_for_civilwar_comic() {
        robo.clickComicMenu()
                .clickSearchButton()
                .findComics("Cap Transport")
                .checkComicScreen("Cap Transport (2005) #20")
                .closeSearchButton()
    }

    @Test
    @Throws(Exception::class)
    fun filter_for_letter_d() {

        robo.clickComicMenu()
                .clickFilterButton()
                .chooseLetterFilter("D")
                .clickButtonOkDialogFilter()
                .checkComicScreen("Deadpool:")
    }

    @Test
    @Throws(Exception::class)
    fun filter_for_all() {
        robo.clickComicMenu()
                .clickFilterButton()
                .chooseLetterFilter("ALL")
                .clickButtonOkDialogFilter()
                .checkComicScreen("Halo")
    }

    @Test
    @Throws(Exception::class)
    fun click_item_list_and_back() {

        robo.clickComicMenu()
                .clickItem(2)
                .backToList()
    }

    @Test
    @Throws(Exception::class)
    fun click_favorite() {
        robo.clickComicMenu()
                .clickOnFavoriteItem(2)
                .clickOffFavoriteItem(2)
    }
}