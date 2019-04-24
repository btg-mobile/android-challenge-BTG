package com.uchoa.btg.movie

import android.app.Application
import com.uchoa.btg.movie.network.MoviesRepository

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MoviesRepository.instance.setUpLanguage(getString(R.string.api_language))
    }
}