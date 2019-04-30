package com.example.desafiobtg.usecases.movielist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.desafiobtg.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.frag_movie_list.*
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import com.example.desafiobtg.usecases.moviedetails.MovieDetailsActivity
import kotlinx.android.synthetic.main.layout_no_internet.*
import javax.inject.Named

class MovieListFragment @Inject constructor(): DaggerFragment(), MovieListContract.View {

    @field:[Inject Named("MovieListPresenter")]
    lateinit var mPresenter: MovieListContract.Presenter

    private var mAdapter: MovieListAdapter? = null
    private var layoutManager = object: LinearLayoutManager(context, LinearLayout.VERTICAL, false) {
        override fun supportsPredictiveItemAnimations() = false
    }

    // Itens para load more
    private val visibleThreshold = 5

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_movie_list, container, false)
        mPresenter.takeView(this)
        mPresenter.setListType(MovieListType.POPULAR)
        return view
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        mPresenter.onViewCreated(this)
    }

    private fun setupListeners() {
        btn_try_again?.setOnClickListener {
            showNoInternet(false)
            mPresenter.loadPopularMovieList()
        }

        rv_movies?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (mPresenter.shouldLoadMoreItems() && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    mPresenter.loadMore()
                }
            }
        })
    }

    override fun notifyItemInserted(position: Int) {
        mAdapter?.notifyItemInserted(position)
    }

    override fun notifyItemRemoved(idx: Int) {
        mAdapter?.notifyItemRemoved(idx)
    }

    override fun notifyItemRangeInserted(position: Int, count: Int) {
        mAdapter?.notifyItemRangeInserted(position, count)
    }

    override fun notifyItemChanged(position: Int) {
        mAdapter?.notifyItemChanged(position)
    }

    override fun notifyFavoriteChanged(index: Int) {
        mAdapter?.notifyItemChanged(index, MovieListAdapter.Payload.FAVORITE_CHANGED)
    }

    private fun setupRecyclerView() {
        rv_movies?.layoutManager = layoutManager
        mAdapter = MovieListAdapter(mPresenter)
        rv_movies?.adapter = mAdapter

        val dividerItemDecoration = DividerItemDecoration(rv_movies?.context , layoutManager.orientation)
        rv_movies.addItemDecoration(dividerItemDecoration)
    }

    override fun notifyDatasetChanged() {
        mAdapter?.notifyDataSetChanged()
    }

    override fun showMovieDetailsActivity(id: String?) {
        id?.let {
            val activity = MovieDetailsActivity.newIntent(context, it)
            startActivity(activity)
        }
    }

    override fun showLoading(shouldShow: Boolean) {
        cl_parent?.let {
            TransitionManager.beginDelayedTransition(it)
        }

        fl_loading?.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    override fun showNoInternet(shouldShow: Boolean) {
        cl_parent?.let {
            TransitionManager.beginDelayedTransition(it)
        }

        import_no_internet?.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    override fun onQueryTextChange(query: String) {
        mPresenter.onQueryTextChange(query)
    }
}