package com.example.moviedb.presentation.movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedb.R
import com.example.moviedb.presentation.details.MovieDetailsActivity
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        viewModel.moviesData.observe(this, Observer {
            it?.let { movies ->
                with(recyclerMovies) {
                    layoutManager =
                        GridLayoutManager(
                            context,
                            2
                        )
                    setHasFixedSize(true)
                    adapter = MoviesAdapter(movies) { movie ->
                        val intent = MovieDetailsActivity.getStartIntent(
                            context,
                            movie.id,
                            movie.title,
                            movie.overview,
                            movie.posterPath,
                            movie.voteAverage,
                            movie.genres
                        )
                        this.context.startActivity(intent)
                    }
                }
            }
        })

        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        mHandler = Handler()
        swiperMovies.isRefreshing = true
        mRunnable = Runnable {
            swiperMovies.isRefreshing = false
        }
        updateView()

        swiperMovies.setOnRefreshListener {
            updateView()
            mRunnable = Runnable {
                swiperMovies.isRefreshing = false
            }
        }
    }

    private fun updateView() {
        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        viewModel.getGenres()
        viewModel.getMovies()

        mHandler.post(mRunnable)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        inflater.inflate(R.menu.main_search, menu)
        val searchItem = menu.findItem(R.id.movie_search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true
                @SuppressLint("DefaultLocale")
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        val search = newText.toLowerCase()
                        viewModel.searchMovieByTitle(search)
                    } else {
                        viewModel.searchMovieByTitle("")
                    }

                    return true
                }
            })

        }

        return super.onCreateOptionsMenu(menu, inflater)
    }


}
