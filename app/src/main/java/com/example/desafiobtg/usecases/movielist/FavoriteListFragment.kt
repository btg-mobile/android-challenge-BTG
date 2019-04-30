package com.example.desafiobtg.usecases.movielist

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.desafiobtg.R
import com.example.desafiobtg.usecases.moviedetails.MovieDetailsActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.frag_favorite_list.*
import javax.inject.Inject
import javax.inject.Named

class FavoriteListFragment @Inject constructor(): DaggerFragment(),
    MovieListContract.View {

    @field:[Inject Named("FavoriteListPresenter")]
    lateinit var mPresenter: MovieListContract.Presenter

    private var mAdapter: MovieListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_favorite_list, container, false)
        mPresenter.takeView(this)
        mPresenter.setListType(MovieListType.FAVORITE)
        return view
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        mPresenter.onViewCreated(this)
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

    override fun showMovieDetailsActivity(id: String?) {
        id?.let {
            val activity = MovieDetailsActivity.newIntent(context, it)
            startActivity(activity)
        }
    }

    override fun notifyDatasetChanged() {
        mAdapter?.notifyDataSetChanged()
    }

    override fun showLoading(shouldShow: Boolean) {
        fl_loading?.visibility = if (shouldShow) View.VISIBLE else View.GONE
    }

    override fun showEmptyList(isEmpty: Boolean) {
        import_empty_list?.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}