package com.example.moviedb.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviedb.R
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

    }
}
