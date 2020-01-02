package com.example.moviedb.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.moviedb.R
import com.example.moviedb.presentation.movies.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar.title = "Movie DB"
        setSupportActionBar(toolBar)

        val fragmentAdapter = MainAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)

        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        viewModel.getMovies(1)

    }
}

