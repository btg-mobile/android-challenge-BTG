package com.arturkida.popularmovies_kotlin.ui.popular

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.ui.BaseFragment

class MoviesFragment : BaseFragment() {

    companion object {
        fun newInstance() = MoviesFragment()
    }

    private lateinit var viewModel: MoviesViewModel

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

        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(MoviesViewModel::class.java)
    }
}
