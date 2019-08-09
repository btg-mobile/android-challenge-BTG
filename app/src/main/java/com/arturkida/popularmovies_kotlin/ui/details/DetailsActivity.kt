package com.arturkida.popularmovies_kotlin.ui.details

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.utils.Constants

class DetailsActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, DetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        Log.i(Constants.LOG_INFO, "Details Activity started")
    }
}
