package com.example.desafiobtg.manager

import com.example.desafiobtg.Preferences
import com.example.desafiobtg.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(private val mPreferences: Preferences){

    val shouldClearLocalMovieData: Boolean
        get() = (System.currentTimeMillis() - (mPreferences.lastUpdateMovies ?: System.currentTimeMillis())) > Constants.TIME_TO_RELOAD_LIST

    fun resetUpdateTime() {
        mPreferences.lastUpdateMovies = (System.currentTimeMillis() - Constants.TIME_TO_RELOAD_LIST) - 100L
    }
}