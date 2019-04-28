package com.example.desafiobtg.usecases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiobtg.R
import com.example.desafiobtg.usecases.movielist.MovieListFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.frag_main.*
import javax.inject.Inject

class MainFragment @Inject constructor(): DaggerFragment(), MainContract.View {

    @Inject
    lateinit var mPresenter: MainContract.Presenter

    @Inject
    lateinit var mMovieListFragment: MovieListFragment

    private var mPagerAdapter : MainPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_main, container, false)
        mPresenter.takeView(this)
        return view
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        mPagerAdapter = MainPagerAdapter(childFragmentManager).apply {
            addPage(mMovieListFragment)
        }

        vp_main?.adapter = mPagerAdapter
    }

}