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
import android.transition.TransitionManager
import com.example.desafiobtg.usecases.moviedetails.MovieDetailsActivity
import kotlinx.android.synthetic.main.layout_no_internet.*
import javax.inject.Named

class MovieListFragment @Inject constructor(): DaggerFragment(), MovieListContract.View {

    @field:[Inject Named("MovieListPresenter")]
    lateinit var mPresenter: MovieListContract.Presenter

    private var mAdapter: MovieListAdapter? = null

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
        setupListener()
        mPresenter.onViewCreated(this)
    }

    private fun setupListener() {
        btn_try_again?.setOnClickListener {
            showNoInternet(false)
            mPresenter.loadPopularMovieList()
        }
    }

    override fun notifyFavoriteChanged(index: Int) {
        mAdapter?.notifyItemChanged(index, MovieListAdapter.Payload.FAVORITE_CHANGED)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        rv_movies?.layoutManager = layoutManager
        mAdapter = MovieListAdapter(mPresenter)
        rv_movies?.adapter = mAdapter

        val dividerItemDecoration = DividerItemDecoration(rv_movies?.context ,layoutManager.orientation)
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
}