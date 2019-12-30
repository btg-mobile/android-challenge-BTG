package com.example.moviedb.presentation.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.moviedb.R
import com.example.moviedb.presentation.movies.MoviesViewModel
import com.example.moviedb.presentation.repository.DataRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    var id :Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)


        id  = intent.getStringExtra(EXTRA_ID).toInt()
        val title = intent.getStringExtra(EXTRA_TITLE)
        val overview = intent.getStringExtra(EXTRA_OVERVIEW)
        val score = intent.getStringExtra(EXTRA_SCORE)


        movieDetailsTitle.text = title
        movieDetailsOverview.text = overview
        movieDetailsScore.text = score


        Picasso.get()
            .load("http://image.tmdb.org/t/p/w780" + intent.getStringExtra(EXTRA_POSTERPATH))
            .into(posterDetails)

        favoriteImageView.setOnClickListener {
            if(DataRepository.toggleFavoriteMovie(id))
                favoriteImageView.setImageResource(R.drawable.favorite)
            else
                favoriteImageView.setImageResource(R.drawable.unfavorite)

        }
    }

    companion object {
        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_OVERVIEW = "EXTRA_OVERVIEW"
        private const val EXTRA_POSTERPATH = "EXTRA_POSTERPATH"
        private const val EXTRA_SCORE = "EXTRA_SCORE"

        fun getStartIntent(
            context: Context,
            id: Int,
            title: String,
            overview: String,
            posterPath: String,
            voteAverage: Number
        ): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(EXTRA_ID, id.toString())
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_OVERVIEW, overview)
                putExtra(EXTRA_POSTERPATH, posterPath)
                putExtra(EXTRA_SCORE, voteAverage.toString())

            }
        }
    }
}
