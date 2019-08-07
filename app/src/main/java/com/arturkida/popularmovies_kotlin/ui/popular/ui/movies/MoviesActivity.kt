package com.arturkida.popularmovies_kotlin.ui.popular.ui.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.data.ApiImpl
import com.arturkida.popularmovies_kotlin.ui.popular.ui.movies.MoviesFragment

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MoviesFragment.newInstance())
                .commitNow()
        }
    }
}
