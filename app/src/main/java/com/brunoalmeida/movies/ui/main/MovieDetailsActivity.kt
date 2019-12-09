package com.brunoalmeida.movies.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.brunoalmeida.movies.R
import com.brunoalmeida.movies.data.model.Movie
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.brunoalmeida.movies.presentation.genders.GendersViewModel


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: GendersViewModel
    var isFavorite: Boolean = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        fab.setImageResource(android.R.drawable.star_off)
        val movieDAO = MainActivity.database?.movieDao()

        val movie = intent.getSerializableExtra(MOVIE) as Movie
        supportActionBar!!.title = movie.title

        val movieFavorite = movieDAO?.findMovieById(movie.uuid)

        if (movieFavorite!!.isPresent) {
            fab.setImageResource(android.R.drawable.star_on)
            isFavorite = true
        }

        viewModel = ViewModelProviders.of(this).get(GendersViewModel::class.java)


        val genresFormart = movie.genreIds!!.replace("[", "").replace("]", "").replace(" ", "").split(",")
        viewModel.getGenreById(genresFormart)

        movieOverview.text = movie.overview
        movieVoteAverage.text = movie.voteAverage
        viewModel.genresLiveData.value!!.forEach {
            movieGenders.text = it.name + ", " + movieGenders.text.toString()
        }
        val url = "https://image.tmdb.org/t/p/w780" + movie.posterPath

        Picasso.get().load(url).into(moviePoster)


        fab.setOnClickListener {

            if (isFavorite) {
                movieDAO.deleteMovie(movie)
                fab.setImageResource(android.R.drawable.star_off)
                isFavorite = false
                val toast = Toast.makeText(applicationContext, "Filme removido da sua lista de favoritos", Toast.LENGTH_LONG)
                toast.show()
            } else {
                movieDAO.insertMovies(movie)
                fab.setImageResource(android.R.drawable.star_on)
                isFavorite = true

                val toast = Toast.makeText(applicationContext, "Filme adicionado na sua lista de favoritos", Toast.LENGTH_LONG)
                toast.show()
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                finishAffinity()
            }
            else -> {
            }
        }
        return true
    }

    override fun onBackPressed() {
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        )
        finishAffinity()
        return
    }

    companion object {
        private const val MOVIE = "MOVIE"

        fun getStartIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE, movie)
            }
        }
    }

}
