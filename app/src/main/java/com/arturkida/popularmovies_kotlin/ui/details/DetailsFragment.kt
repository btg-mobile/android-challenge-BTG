package com.arturkida.popularmovies_kotlin.ui.details

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturkida.popularmovies_kotlin.BuildConfig
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var movie: Movie
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
        loadMovieInfo()
        setViewModel()
        setListeners()
    }

    private fun setListeners() {
        iv_favorite_star.setOnClickListener {
            if (!movie.favorite) {
                viewModel.addMovie(movie)
                movie.favorite = true
            } else {
                viewModel.deleteMovie(movie)
                movie.favorite = false
            }

            updateFavoriteIcon()
        }
    }

    private fun getIntents() {
        activity?.intent?.let {
            movie = it.getParcelableExtra(Constants.INTENT_MOVIE_INFO)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
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
        tv_details_movie_genres.text = movie.genre_names

        updateFavoriteIcon()
    }

    private fun updateFavoriteIcon() {
        if (movie.favorite) {
            iv_favorite_star.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            iv_favorite_star.setImageResource(android.R.drawable.btn_star_big_off)
        }
    }
}
