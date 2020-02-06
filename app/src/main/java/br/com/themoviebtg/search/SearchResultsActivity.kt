package br.com.themoviebtg.search

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import br.com.themoviebtg.R
import br.com.themoviebtg.movies.behavior.MoviesAdapter
import kotlinx.android.synthetic.main.activity_search_result.*


class SearchResultsActivity : Activity(), SearchResultView {
    private val presenter = SearchResultPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            this.presenter.fetchMoviesByQuery(query)
        }
    }

    override fun initGridView(moviesAdapter: MoviesAdapter) {
        gv_search_result.adapter = moviesAdapter
    }

    override fun showProgress() {
        pb_search_result.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_search_result.visibility = View.GONE
    }
}

