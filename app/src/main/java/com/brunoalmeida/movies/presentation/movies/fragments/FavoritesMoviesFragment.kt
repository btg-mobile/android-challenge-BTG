package com.brunoalmeida.movies.presentation.movies.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.brunoalmeida.movies.R
import com.brunoalmeida.movies.presentation.movies.MoviesAdapter
import com.brunoalmeida.movies.presentation.movies.MoviesViewModel
import com.brunoalmeida.movies.ui.main.MovieDetailsActivity
import kotlinx.android.synthetic.main.fragment_favorites_movies.*

class FavoritesMoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel

    private var querySearch: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites_movies, container, false)
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        progressFavorites.visibility = ProgressBar.VISIBLE
        viewModel.favoritesMoviesLiveData.observe(this, Observer {
            if(it.isNotEmpty()) {
                recycleMoviesFavorites.visibility = View.VISIBLE
                emptyViewFavorites.visibility = View.GONE
            } else {
                emptyViewFavorites.text = getString(R.string.empty_list_movie)
                recycleMoviesFavorites.visibility = View.GONE
                emptyViewFavorites.visibility = View.VISIBLE
            }
            progressFavorites.visibility = ProgressBar.GONE

            it?.let { movies ->
                with(recycleMoviesFavorites) {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = MoviesAdapter(movies) { movie ->
                        val intent =
                            MovieDetailsActivity.getStartIntent(
                                context,
                                movie
                            )

                        this@FavoritesMoviesFragment.startActivity(intent)
                    }
                }
            }
        })
        viewModel.getFavoritesMovies(null)

        searchButtonFavorites.setOnClickListener {
            if (querySearch != "") {
                viewModel.getFavoritesMovies(querySearch)
            } else {
                viewModel.getFavoritesMovies(null)
            }
        }

        searchViewFavorites.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                querySearch = s.toString()
            }

        })
    }

}

