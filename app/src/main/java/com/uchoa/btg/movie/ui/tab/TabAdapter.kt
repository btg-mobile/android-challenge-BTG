package com.uchoa.btg.movie.ui.tab

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.uchoa.btg.movie.R
import com.uchoa.btg.movie.ui.movielist.MoviesFragment

class TabAdapter(var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var movieFragment: MoviesFragment = MoviesFragment.newInstance(false)
    private var favoriteFragment: MoviesFragment = MoviesFragment.newInstance(true)

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return movieFragment
            }
            1 -> {
                return favoriteFragment
            }
        }
        return movieFragment
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.tab_name_home)
            1 -> return context.getString(R.string.tab_name_favorite)
        }
        return null
    }

    fun search(query: String) {
        movieFragment.filter(query)
        favoriteFragment.filter(query)
    }

    companion object {
        var TAB_COUNT = 2
    }
}
