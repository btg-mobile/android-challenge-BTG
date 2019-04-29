package com.example.desafiobtg.usecases.moviedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.desafiobtg.R
import com.example.desafiobtg.usecases.moviedetails.MovieDetailsFragment.Companion.MOVIE_ID_KEY
import com.example.desafiobtg.utils.ActivityUtils
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MovieDetailsActivity @Inject constructor(): DaggerAppCompatActivity() {

    companion object {
        fun newIntent(context: Context?, movieId: String) = Intent(context, MovieDetailsActivity::class.java).apply {
            putExtra(MOVIE_ID_KEY, movieId)
        }
    }

    @Inject
    lateinit var injectedFragment: MovieDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        var movieDetailsFragment: MovieDetailsFragment? = supportFragmentManager.findFragmentById(R.id.fragment_container) as MovieDetailsFragment?

        if (movieDetailsFragment == null) {
            movieDetailsFragment = injectedFragment
            ActivityUtils.addFragmentToActivity(supportFragmentManager, movieDetailsFragment, R.id.fragment_container)
        }

        movieDetailsFragment.arguments = intent?.extras
    }
}
