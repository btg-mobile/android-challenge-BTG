package com.example.moviedb.presentation.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviedb.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val score =  intent.getStringExtra(EXTRA_SCORE)

        movieDetailsTitle.text = intent.getStringExtra(EXTRA_TITLE)
        movieDetailsOverview.text = intent.getStringExtra(EXTRA_OVERVIEW)
        movieDetailsScore.text =score

        Picasso.get().load("http://image.tmdb.org/t/p/w780" + intent.getStringExtra(EXTRA_POSTERPATH)).into(posterDetails)
    }

    companion object {
        private const val EXTRA_TITLE  = "EXTRA_TITLE"
        private const val EXTRA_OVERVIEW = "EXTRA_OVERVIEW"
        private const val EXTRA_POSTERPATH = "EXTRA_POSTERPATH"
        private const val EXTRA_SCORE = "EXTRA_SCORE"

        fun getStartIntent(context: Context, title: String, overview: String, posterPath:String, voteAverage: Number): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_OVERVIEW, overview)
                putExtra(EXTRA_POSTERPATH, posterPath)
                putExtra(EXTRA_SCORE, voteAverage.toString())

            }
        }
    }
}
