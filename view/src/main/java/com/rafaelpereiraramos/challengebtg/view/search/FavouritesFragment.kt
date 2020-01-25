package com.rafaelpereiraramos.challengebtg.view.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rafaelpereiraramos.challengebtg.view.R
import com.rafaelpereiraramos.challengebtg.view.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_favourites.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FavouritesFragment : Fragment() {

    private val viewModel by sharedViewModel<SearchViewModel>()
    private lateinit var adapter: FavouritesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = FavouritesAdapter(GlideApp.with(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        container_list.adapter = adapter

        bindLiveData()
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshFavourites()
    }

    private fun bindLiveData() {
        viewModel.favourites.observe(this, Observer {
            adapter.updateFavourites(it)
        })

        viewModel.filterFavourites.observe(this, Observer {
            adapter.filter.filter(it)
        })
    }
}