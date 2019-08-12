package com.arturkida.popularmovies_kotlin.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.arturkida.popularmovies_kotlin.R

class DetailsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, DetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setBackButton()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_details, DetailsFragment.newInstance())
                .commitNow()
        }
    }

    private fun setBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
