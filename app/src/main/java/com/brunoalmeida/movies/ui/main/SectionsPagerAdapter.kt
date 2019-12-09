package com.brunoalmeida.movies.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.brunoalmeida.movies.R
import com.brunoalmeida.movies.presentation.movies.fragments.FavoritesMoviesFragment
import com.brunoalmeida.movies.presentation.movies.fragments.MoviesFragment

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MoviesFragment()
            }
            else -> {
                return FavoritesMoviesFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Filmes"
            1 -> "Favoritos"
            else -> {
                return "Third Tab"
            }
        }
    }

    override fun getCount() = 2
}