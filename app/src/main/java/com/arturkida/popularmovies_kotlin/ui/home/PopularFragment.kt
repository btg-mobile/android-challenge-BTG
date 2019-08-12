package com.arturkida.popularmovies_kotlin.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import com.arturkida.popularmovies_kotlin.R
import com.arturkida.popularmovies_kotlin.adapter.MoviesListAdapter
import com.arturkida.popularmovies_kotlin.model.Genre
import com.arturkida.popularmovies_kotlin.model.Movie
import com.arturkida.popularmovies_kotlin.ui.BaseFragment
import com.arturkida.popularmovies_kotlin.ui.details.DetailsActivity
import com.arturkida.popularmovies_kotlin.utils.Constants
import com.arturkida.popularmovies_kotlin.utils.SearchType
import kotlinx.android.synthetic.main.fragment_movies.*

class PopularFragment : BaseFragment(), MoviesListAdapter.MovieItemClickListener {

    private var genresList = mutableListOf<Genre>()
    private var moviesList = mutableListOf<Movie>()

    private lateinit var viewModel: MoviesViewModel
    private val adapter: MoviesListAdapter by lazy {
            MoviesListAdapter(context, moviesList, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFragment()
    }

    private fun setupFragment() {
        setViewModel()
        setRecyclerView()
        setObservers()
        setListeners()
        removeFocus()

        getPopularMovies()
    }

    private fun setListeners() {
        setSearchListenerByMovieTitle()
        setSwipeToRefreshListener()
    }

    private fun setSwipeToRefreshListener() {
        swipe_movie_list.setOnRefreshListener {
            getPopularMovies()
        }
    }

    private fun setSearchListenerByMovieTitle() {
        et_search_popular_movies.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clearMoviesList()
                searchMoviesBy(SearchType.TITLE, et_search_popular_movies)
                hideKeyboard()
            }
            false
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.window?.currentFocus?.windowToken, 0)
    }

    private fun searchMoviesBy(searchType: SearchType, searchBar: EditText) {
        viewModel.popularMovies?.value?.let { popularList ->

            val searchString = searchBar.text.toString()

            if (searchString.isBlank()) {
                moviesList.addAll(popularList)
                adapter.updateMovies(popularList)
                showMovieScreen(popularList)
            } else {
                val filteredMovies = viewModel.searchMovies(
                    searchString,
                    searchType,
                    popularList
                )

                updateMoviesListBy(filteredMovies, searchBar)
                showMovieScreen(filteredMovies)
            }
        }
    }

    private fun updateMoviesListBy(filteredMovies: MutableList<Movie>, searchBar: EditText) {
        Log.i(Constants.LOG_INFO, "Updating favorite movies list with search by title")
        moviesList.addAll(filteredMovies)
        adapter.updateMovies(filteredMovies)
        searchBar.text.clear()
    }

    private fun clearMoviesList() {
        moviesList.clear()
    }

    private fun removeFocus() {
        fragment_popular_movies.requestFocus()
    }

    private fun setObservers() {
        viewModel.genres.observe(this, Observer { genres ->
            genres?.let {
                genresList.addAll(it)

                // Retry
                if (genresList.isEmpty()) {
                    viewModel.retryGetGenres()
                }
            }
        })

        viewModel.popularMovies?.observe(this, Observer { movies ->
            movies?.let {
                updateMoviesList(it)
                updateMoviesFavoriteStatus()
                updateAdapter()
                showMovieScreen(it)
                swipe_movie_list.isRefreshing = false
            }
        })

        viewModel.favoriteMovies?.observe(this, Observer { movies ->
            movies?.let {
                updateMoviesFavoriteStatus()
                updateAdapter()
            }
        })
    }

    private fun updateMoviesList(it: List<Movie>) {
        clearMoviesList()
        moviesList.addAll(it)
    }

    private fun updateAdapter() {
        adapter.updateMovies(moviesList)
    }

    private fun updateMoviesFavoriteStatus() {
        moviesList.forEach {
            viewModel.updateFavoriteStatusOf(it)
        }
    }

    private fun showMovieScreen(moviesList: List<Movie>) {
        hideProgressBar()

        if (viewModel.mustShowMoviesList(moviesList)) {
            showPopularMoviesList()
        } else {
            showEmptyListMessage()
        }
    }

    private fun showEmptyListMessage() {
        rv_popular_movie_list.visibility = View.GONE
        tv_empty_popular_list.visibility = View.VISIBLE
    }

    private fun showPopularMoviesList() {
        rv_popular_movie_list.visibility = View.VISIBLE
        tv_empty_popular_list.visibility = View.GONE
    }

    private fun showProgressBar() {
        progress_bar_popular.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar_popular.visibility = View.GONE
    }

    private fun setRecyclerView() {
        rv_popular_movie_list.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        context?.let {
            rv_popular_movie_list.adapter = adapter
        }
    }

    private fun getPopularMovies() {
        viewModel.getPopularMovies()
    }

    private fun setViewModel() {
        context?.let {
            viewModel = ViewModelProviders.of(activity!!,
                viewModelFactory { MoviesViewModel(it) })
                .get(MoviesViewModel::class.java)
        }
    }

    override fun updateFavorite(position: Int) {
        if (moviesList[position].favorite) {
            viewModel.populateGenresNameFrom(moviesList[position])
            viewModel.addFavoriteMovie(moviesList[position])
        } else {
            viewModel.deleteFavoriteMovie(moviesList[position])
        }
    }

    override fun onClick(position: Int) {
        var movie = moviesList[position]

        movie = viewModel.populateGenresNameFrom(movie)

        val intent = DetailsActivity.getIntent(context)

        intent.putExtra(Constants.INTENT_MOVIE_INFO, movie)

        startActivity(intent)

        Log.i(Constants.LOG_INFO, "Starting Details Activity from popular movies list")
        Log.i(Constants.LOG_INFO, "Movie data: $movie")
    }
}
