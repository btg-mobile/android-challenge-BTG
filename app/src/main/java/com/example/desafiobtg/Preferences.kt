package com.example.desafiobtg

import android.content.Context
import com.example.desafiobtg.di.qualifiers.utils.ApplicationContext
import com.google.gson.Gson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(@ApplicationContext val context: Context, val gson: Gson) {

    private val PREFS_NAME = "com.example.desafiobtg.MoviesPrefsFile"

    private val LAST_UPDATE_MOVIES = "lastUpdateMovies"

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var lastUpdateMovies: Long?
        get() = prefs.getLong(LAST_UPDATE_MOVIES, -1L).takeIf { it != -1L }
        set(value) = prefs.edit().putLong(LAST_UPDATE_MOVIES, value ?: -1).apply()

}