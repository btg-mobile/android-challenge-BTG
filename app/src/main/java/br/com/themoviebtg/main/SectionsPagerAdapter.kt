package br.com.themoviebtg.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.themoviebtg.R
import br.com.themoviebtg.favorites.ui.FavoritesFragment
import br.com.themoviebtg.movies.ui.MoviesFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val fragmentList = listOf(
        MoviesFragment(),
        FavoritesFragment()
    )

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = fragmentList.size
}