package com.rafaelpereiraramos.challengebtg.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rafaelpereiraramos.challengebtg.view.R
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    private val viewModel by sharedViewModel<SearchViewModel>()

    private lateinit var adapter: SearchFragmentPagerAdapter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPager()
    }

    private fun initPager() {
        adapter = SearchFragmentPagerAdapter(this)
        adapter.pages.add(SearchResultFragment())
        adapter.pages.add(FavouritesFragment())

        pager.adapter = adapter

        val tabLayoutMediator = TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = if (adapter.pages.size > position && adapter.pages[position] is SearchResultFragment) {
                getString(R.string.search_fragment_result_page)
            } else {
                getString(R.string.search_fragment_favourites)
            }
        }

        tabLayoutMediator.attach()
    }
}