package com.example.desafiobtg.usecases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiobtg.R
import com.example.desafiobtg.usecases.movielist.FavoriteListFragment
import com.example.desafiobtg.usecases.movielist.MovieListFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.frag_main.*
import javax.inject.Inject

class MainFragment @Inject constructor(): DaggerFragment() {

    @Inject
    lateinit var mMovieListFragment: MovieListFragment

    @Inject
    lateinit var mFavoriteListFragment: FavoriteListFragment

    private var mPagerAdapter : MainPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        mPagerAdapter = MainPagerAdapter(childFragmentManager).apply {
            addPage(mMovieListFragment, getString(R.string.popular_films))
            addPage(mFavoriteListFragment, getString(R.string.favorite_films))
        }

        vp_main?.adapter = mPagerAdapter
        tl_main?.setupWithViewPager(vp_main)
    }

    fun onQueryTextChange(query: String) {
        mMovieListFragment.onQueryTextChange(query)
        mFavoriteListFragment.onQueryTextChange(query)
    }

}