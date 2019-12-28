package org.challenge.btg.ui.presentation.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.challenge.btg.R
import org.challenge.btg.ui.presentation.FavoriteFragment
import org.challenge.btg.ui.presentation.MoviesFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_movie,
    R.string.tab_favorite
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment()
            else -> FavoriteFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}