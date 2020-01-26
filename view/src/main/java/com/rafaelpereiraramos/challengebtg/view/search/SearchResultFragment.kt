package com.rafaelpereiraramos.challengebtg.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.utils.GlideApp
import kotlinx.android.synthetic.main.search_result_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchResultFragment : Fragment() {

    private val viewModel by sharedViewModel<SearchViewModel>()
    private lateinit var adapter: SearchResultAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = SearchResultAdapter(GlideApp.with(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container_list.adapter = adapter

        bindLiveData()
        setEvents()

        if (savedInstanceState == null && !viewModel.isQueried()) {
            viewModel.loadPopularMovies()
        }
    }

    private fun setEvents() {
        swipe_refresh.setOnRefreshListener {
            viewModel.refreshSearch()
        }
    }

    private fun bindLiveData() {
        viewModel.movies.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}