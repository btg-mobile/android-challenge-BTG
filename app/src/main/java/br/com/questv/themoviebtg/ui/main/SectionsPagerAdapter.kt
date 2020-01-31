package br.com.questv.themoviebtg.ui.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.questv.themoviebtg.R
import br.com.questv.themoviebtg.ui.favorites.FavoritesFragment
import br.com.questv.themoviebtg.genres.ui.GenresFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val fragmentList = listOf(GenresFragment(), FavoritesFragment())

    override fun getItem(position: Int) = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount() = fragmentList.size
}