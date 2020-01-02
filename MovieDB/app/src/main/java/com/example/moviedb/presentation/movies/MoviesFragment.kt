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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.R
import com.example.moviedb.data.model.Movie
import com.example.moviedb.presentation.details.MovieDetailsActivity
import com.example.moviedb.presentation.repository.DataRepository
import kotlinx.android.synthetic.main.fragment_movies.*

class MoviesFragment : Fragment() {

    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var visibleItemCount: Int = 0
    private var pastVisibleItemCount: Int = 0
    private var totalItemCount : Int = 0
    private var pagId :Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val observer = Observer<List<Movie>> {
            it?.let { movies ->
                with(recyclerMovies) {
                    layoutManager =
                        GridLayoutManager(
                            context,
                            2
                        )
                    scrollToPosition((layoutManager as LinearLayoutManager)?.findLastVisibleItemPosition())
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
        }

        DataRepository.createPopularObserver(this, observer)

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

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)

        if (menuVisible)
            DataRepository.restorePopularData()

    }

    private fun updateView() {

        DataRepository.restorePopularData()

        val viewModel: MoviesViewModel =
            ViewModelProviders.of(this).get(MoviesViewModel::class.java)

        recyclerMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0) {
                    visibleItemCount = recyclerView.layoutManager?.childCount!!
                    totalItemCount = recyclerView.layoutManager?.itemCount!!
                    pastVisibleItemCount = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                        if ((visibleItemCount + pastVisibleItemCount) >= totalItemCount) {
                            pagId++
                            viewModel.getMovies(pagId)
                        }

                }
            }
        })

        mHandler.post(mRunnable)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
                        DataRepository.searchMovieByTitle(search)
                    } else {
                        DataRepository.restorePopularData()
                    }

                    return true
                }
            })

        }

        return super.onCreateOptionsMenu(menu, inflater)
    }


}
