package com.arturkida.popularmovies_kotlin.ui.details

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.data.local.AppDatabase
import com.arturkida.popularmovies_kotlin.data.local.MovieDao
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var movieDao: MovieDao

    private val movie: Movie by lazy {
        Log.i(Constants.LOG_INFO, "Getting intent from Movies List")
        intent.getParcelableExtra<Movie>(Constants.INTENT_MOVIE_INFO)
    }

    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, DetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        Log.i(Constants.LOG_INFO, "Details Activity started")

        setBackButton()
        setDatabase()
        loadMovieInfo()
    }

    private fun setBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setDatabase() {
        val database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            Constants.MOVIES_DATABASE
        ).build()

        movieDao = database.movieDao()
    }

    private fun loadMovieInfo() {
        val posterPath = BuildConfig.BASE_IMAGE_URL + movie.poster_path

        Glide.with(this)
            .load(posterPath)
            .into(iv_details_movie_poster)

        tv_details_movie_title.text = movie.title
        tv_details_movie_description.text = movie.overview
        tv_details_movie_year.text = movie.release_date
        tv_details_movie_rate.text = movie.vote_average.toString()
        tv_details_movie_genres.text = movie.title
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
