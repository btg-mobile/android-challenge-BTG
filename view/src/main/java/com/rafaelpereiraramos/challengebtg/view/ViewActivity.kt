package com.rafaelpereiraramos.challengebtg.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        setSupportActionBar(toolbar)

        navController = (app_navhost as NavHost).navController
    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = destination.label

            if (destination.id == R.id.details_movie) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
            }
        }

        toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }
}