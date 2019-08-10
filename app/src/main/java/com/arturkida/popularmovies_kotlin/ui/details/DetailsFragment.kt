package com.arturkida.popularmovies_kotlin.ui.details

import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.data.local.AppDatabase
import com.arturkida.popularmovies_kotlin.data.local.MovieDao
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var movie: Movie
    private lateinit var movieDao: MovieDao
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFragment()
    }

    private fun setupFragment() {
        getIntents()
        setDatabase()
        loadMovieInfo()
        setViewModel()
    }

    private fun getIntents() {
        activity?.intent?.let {
            movie = it.getParcelableExtra(Constants.INTENT_MOVIE_INFO)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    }

    private fun setDatabase() {

        context?.let {
            val database = Room.databaseBuilder(
                it,
                AppDatabase::class.java,
                Constants.MOVIES_DATABASE
            ).build()

            movieDao = database.movieDao()
        }
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

}
