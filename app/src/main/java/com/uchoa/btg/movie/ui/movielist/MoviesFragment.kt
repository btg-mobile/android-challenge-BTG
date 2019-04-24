package com.uchoa.btg.movie.ui.movielist

import android.app.ActivityOptions
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uchoa.btg.movie.R
import com.uchoa.btg.movie.callbacks.OnMoviesClickCallback
import com.uchoa.btg.movie.models.Movie
import com.uchoa.btg.movie.ui.moviedetails.MovieDetailActivity
import com.uchoa.btg.movie.utils.Constants
import com.uchoa.btg.movie.utils.FontStyleUtil
import kotlinx.android.synthetic.main.activity_movies.*
import android.util.Pair as UtilPair

class MoviesFragment : Fragment(), MoviesContract.View,
    OnMoviesClickCallback, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: MoviesPresenter
    private var adapter: MoviesAdapter? = null
    private var filterMode: Boolean = false
    private var favoriteMode: Boolean = false
    private val manager = LinearLayoutManager(context)
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var search: String = ""

    companion object {

        const val MOVIE_ID = "movie-id"
        const val UPDATE_FAVORITE_MESSAGE = "update-favorite"

        fun newInstance(favoriteMode: Boolean): MoviesFragment {
            val fragment = MoviesFragment()
            fragment.favoriteMode = favoriteMode
            return fragment
        }
    }

    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (favoriteMode) {
                adapter = null
                presenter.getFavoriteMovies()
                filter(search)
            } else {
                val movieUpdatedId = intent.getIntExtra(MOVIE_ID, 0)
                adapter?.updateMovies(movieUpdatedId)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LocalBroadcastManager.getInstance(context!!).registerReceiver(
            messageReceiver, IntentFilter(UPDATE_FAVORITE_MESSAGE))

        val rootView = inflater.inflate(R.layout.activity_movies, container, false)
        configurePullToRefresh(rootView)
        return rootView
    }

    private fun configurePullToRefresh(rootView: View) {
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_container) as SwipeRefreshLayout
        swipeRefreshLayout?.setOnRefreshListener(this)
        swipeRefreshLayout?.setColorSchemeResources(R.color.colorAccent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MoviesPresenter(context!!, this)
        recyclerView.layoutManager = manager
        Handler().postDelayed({ loadMovies() }, 1000)
    }

    private fun loadMovies() {
        swipeRefreshLayout?.isRefreshing = true
        if (favoriteMode) {
            presenter.getFavoriteMovies()
        } else {
            setupOnScrollListener()
            presenter.getMovies()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(messageReceiver)
    }

    private fun setupOnScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = manager.itemCount
                val visibleItemCount = manager.childCount
                val firstVisibleItem = manager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount - 5) {
                    if (!presenter.isLoadingMovie() && !filterMode) {
                        presenter.getMovies()
                    }
                }
            }
        })
    }

    fun filter(query: String) {
        search = query
        filterMode = !query.isEmpty()
        adapter?.filterMovies(search)
    }

    override fun onRefresh() {
        if (!favoriteMode) {
            presenter.resetCurrentPage()
            loadMovies()
        } else {
            swipeRefreshLayout?.isRefreshing = false
        }
    }

    override fun onCellClick(view: View, rate: View, movie: Movie) {
        val intent = Intent(activity, MovieDetailActivity::class.java)

        intent.putExtra(MovieDetailActivity.MOVIE_ID, movie.id)
        intent.putExtra(MovieDetailActivity.MOVIE_TITLE, movie.title)
        intent.putExtra(MovieDetailActivity.MOVIE_FAVORITE, movie.favorite)

        val p1 = UtilPair.create<View, String>(view, getString(R.string.shared_element_movie_title))
        val p2 = UtilPair.create<View, String>(rate, getString(R.string.shared_element_movie_rate))

        val options = ActivityOptions.makeSceneTransitionAnimation(activity!!, p1, p2)
        startActivity(intent, options.toBundle())
    }

    override fun onRateClick(movie: Movie) {
        presenter.updateFavoriteStatus(movie)
    }

    override fun showMovies(movies: MutableList<Movie>) {
        recyclerView.visibility = View.VISIBLE
        errorContainer.visibility = View.GONE
        if (adapter == null) {
            adapter = MoviesAdapter(movies, this)
            recyclerView.adapter = adapter
        } else {
            adapter?.appendMovies(movies)
        }
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showEmptyView() {
        recyclerView.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        movieErrorMessage.visibility = View.GONE
        swipeRefreshLayout?.isRefreshing = false
    }

    override fun showError() {
        if (adapter != null) {
            Toast.makeText(context, getString(R.string.movies_server_error_message), Toast.LENGTH_SHORT).show()
        } else {
            recyclerView.visibility = View.GONE
            errorContainer.visibility = View.VISIBLE
            movieErrorMessage.visibility = View.VISIBLE
            FontStyleUtil.applyFontStyle(context!!, movieErrorMessage, Constants.FONT_STYLE_COMIC_SANS)
        }

        swipeRefreshLayout?.isRefreshing = false
    }
}