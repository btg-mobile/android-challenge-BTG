package com.brunoalmeida.movies.presentation.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunoalmeida.movies.presentation.movies.MoviesAdapter
import com.brunoalmeida.movies.ui.main.MovieDetailsActivity
import com.brunoalmeida.movies.presentation.movies.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import com.brunoalmeida.movies.R
import android.view.*
import android.widget.ProgressBar
import com.brunoalmeida.movies.data.model.Movie
import android.text.Editable
import android.text.TextWatcher
import java.lang.Exception


class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel

    private var loading = true
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    private var page: Int = 1
    private var querySearch: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        viewModel.moviesLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                recycleMovies.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            } else {
                emptyView.text = getString(R.string.empty_list_movie)
                recycleMovies.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            }
            progress.visibility = ProgressBar.GONE
            it?.let { movies ->
                with(recycleMovies) {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)

                    adapter = MoviesAdapter(movies) { movie ->
                        val intent =
                            MovieDetailsActivity.getStartIntent(
                                context,
                                movie
                            )

                        this@MoviesFragment.startActivity(intent)
                    }
                }
            }
        })
        try {
            viewModel.getMovies(page, this@MoviesFragment)
        } catch (e: Exception) {
            emptyView.text = getString(R.string.error_system)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onStart() {
        super.onStart()
        val mLayoutManager = LinearLayoutManager(context)
        recycleMovies.layoutManager = mLayoutManager
        progress.visibility = ProgressBar.VISIBLE

        recycleMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = recyclerView.childCount
                    totalItemCount = recycleMovies.adapter!!.itemCount
                    pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            page += 1
                            viewModel.getMovies(page, this@MoviesFragment)
                            progress.visibility = ProgressBar.VISIBLE
                        }
                    }
                }
            }
        })

        searchButton.setOnClickListener {
            if (querySearch != "") {
                try {
                    viewModel.searchMovies(1, querySearch, this@MoviesFragment)
                } catch (e: Exception) {
                    emptyView.text = getString(R.string.error_system)
                }
            } else {
                try {
                    viewModel.getMovies(1, this@MoviesFragment)
                } catch (e: Exception) {
                    emptyView.text = getString(R.string.error_system)
                }
            }
        }

        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                querySearch = s.toString()
            }

        })
    }

    fun showMoreMovieList(data: List<Movie>) {
        viewModel.moviesLiveData.value = viewModel.moviesLiveData.value!!.plus(data)
        recycleMovies.adapter!!.notifyDataSetChanged()
        progress.visibility = ProgressBar.GONE
        loading = true
    }

}