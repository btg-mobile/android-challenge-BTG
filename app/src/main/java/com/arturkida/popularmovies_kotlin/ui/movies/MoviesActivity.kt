package com.arturkida.popularmovies_kotlin.ui.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arturkida.popularmovies_kotlin.R

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PopularFragment.newInstance())
                .commitNow()
        }
    }
}
