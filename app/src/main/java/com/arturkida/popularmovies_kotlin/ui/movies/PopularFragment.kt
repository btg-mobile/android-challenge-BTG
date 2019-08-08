package com.arturkida.popularmovies_kotlin.ui.movies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.ui.BaseFragment
import com.arturkida.popularmovies_kotlin.utils.Constants
import kotlinx.android.synthetic.main.movies_fragment.*

class PopularFragment : BaseFragment() {

    companion object {
        fun newInstance() = PopularFragment()
    }

    private lateinit var viewModel: MoviesViewModel

    private var genresList = mutableListOf<Genre>()
    private var moviesList = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFragment()
    }

    fun setupFragment() {
        setViewModel()
        setRecyclerView()
        setObservers()

        getPopularMovies()
    }

    private fun setObservers() {
        viewModel.genres.observe(this, Observer { genres ->
            genres?.let {
                genresList.addAll(it)
                Log.i(Constants.LOG_INFO, "Genres updated")
            }
        })

        viewModel.popularMovies.observe(this, Observer { movies ->
            movies?.let {
                moviesList.addAll(it)
                Log.i(Constants.LOG_INFO, "Movies updated")
            }
        })
    }

    private fun setRecyclerView() {
        val spanCount = 2
        rv_movie_list.layoutManager = GridLayoutManager(context, spanCount)
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(MoviesViewModel::class.java)
    }
}
