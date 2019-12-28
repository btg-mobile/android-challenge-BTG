package org.challenge.btg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.challenge.btg.ui.presentation.adapter.SectionsPagerAdapter
import org.challenge.btg.utils.Global

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        Global.pathCache = applicationContext.cacheDir.toString()
    }
}