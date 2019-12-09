package com.brunoalmeida.movies.ui.main

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.brunoalmeida.movies.R
import com.brunoalmeida.movies.data.AppDatabase
import com.brunoalmeida.movies.presentation.genders.GendersViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GendersViewModel

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        database = Room.databaseBuilder(this, AppDatabase::class.java, "tmdb").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(GendersViewModel::class.java)
        viewModel.getGenders()


    }


}