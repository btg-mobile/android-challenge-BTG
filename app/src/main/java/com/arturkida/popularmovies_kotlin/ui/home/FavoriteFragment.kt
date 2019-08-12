package com.arturkida.popularmovies_kotlin.ui.home

import android.app.Activity
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
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : BaseFragment(), MoviesListAdapter.MovieItemClickListener {

    private var genresList = mutableListOf<Genre>()
    private var moviesList = mutableListOf<Movie>()

    private lateinit var viewModel: MoviesViewModel
    private val adapter: MoviesListAdapter by lazy {
        MoviesListAdapter(context, moviesList, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFragment()
    }

    fun setupFragment() {
        setViewModel()
        setRecyclerView()
        setObservers()
        setListeners()
        removeFocus()
    }

    private fun setViewModel() {
        context?.let {
            viewModel = ViewModelProviders.of(activity!!,
                viewModelFactory { MoviesViewModel(it) })
                .get(MoviesViewModel::class.java)
        }
    }

    private fun clearMoviesList() {
        moviesList.clear()
    }

    private fun setListeners() {
        setSearchListenerByMovieTitle()
        setSearchListenerByMovieYear()
    }

    private fun setSearchListenerByMovieTitle() {
        et_search_favorite_movies_title.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clearMoviesList()
                searchMoviesBy(SearchType.TITLE, et_search_favorite_movies_title)
                hideKeyboard()
            }
            false
        }
    }

    private fun setSearchListenerByMovieYear() {
        et_search_favorite_movies_year.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clearMoviesList()
                searchMoviesBy(SearchType.YEAR, et_search_favorite_movies_year)
                hideKeyboard()
            }
            false
        }
    }

    private fun searchMoviesBy(searchType: SearchType, searchBar: EditText) {
        viewModel.favoriteMovies?.value?.let { favoritesList ->

            val searchString = searchBar.text.toString()

            if (searchString.isBlank()) {
                moviesList.addAll(favoritesList)
                adapter.updateMovies(favoritesList)
                showMovieScreen(favoritesList)
            } else {
                val filteredMovies = viewModel.searchMovies(
                    searchString,
                    searchType,
                    favoritesList
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
        showMovieScreen(filteredMovies)
        searchBar.text.clear()
    }

    private fun removeFocus() {
        fragment_favorite_movies.requestFocus()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.window?.currentFocus?.windowToken, 0)
    }

    private fun setObservers() {
        viewModel.genres.observe(this, Observer { genres ->
            genres?.let {
                genresList.addAll(it)
                Log.i(Constants.LOG_INFO, "Genres updated")
            }
        })

        viewModel.favoriteMovies?.observe(this, Observer { favoritesList ->
            moviesList.clear()

            favoritesList?.let {
                moviesList.addAll(it)
                showMovieScreen(it)
            }

            adapter.updateMovies(moviesList)
        })
    }

    private fun showMovieScreen(moviesList: List<Movie>) {
        hideProgressBar()

        if (viewModel.mustShowMoviesList(moviesList)) {
            showFavoriteMoviesList()
        } else {
            showEmptyListMessage()
        }
    }

    private fun showEmptyListMessage() {
        rv_favorite_movie_list.visibility = View.GONE
        tv_empty_favorites_list.visibility = View.VISIBLE
    }

    private fun showFavoriteMoviesList() {
        rv_favorite_movie_list.visibility = View.VISIBLE
        tv_empty_favorites_list.visibility = View.GONE
    }

    private fun hideProgressBar() {
        progress_bar_favorites.visibility = View.GONE
    }

    private fun setRecyclerView() {
        rv_favorite_movie_list.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        context?.let {
            rv_favorite_movie_list.adapter = adapter
        }
    }

    override fun updateFavorite(position: Int) {
        if (moviesList[position].favorite) {
            viewModel.addFavoriteMovie(moviesList[position])
        } else {
            viewModel.deleteFavoriteMovie(moviesList[position])
        }
    }

    override fun onClick(position: Int) {
        val movie = moviesList[position]
        val intent = DetailsActivity.getIntent(context)

        intent.putExtra(Constants.INTENT_MOVIE_INFO, movie)

        startActivity(intent)

        Log.i(Constants.LOG_INFO, "Starting Details Activity from favorite movies list")
        Log.i(Constants.LOG_INFO, "Movie data: $movie")
    }
}
