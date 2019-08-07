package com.arturkida.popularmovies_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.arturkida.popularmovies_kotlin.data.ApiImpl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = ApiImpl()

        api.getGenres()
    }
}