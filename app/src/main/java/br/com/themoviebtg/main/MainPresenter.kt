package br.com.themoviebtg.main

import androidx.viewpager.widget.ViewPager
import br.com.themoviebtg.R
import br.com.themoviebtg.favorites.ui.FavoritesFragment
import br.com.themoviebtg.movies.ui.MoviesFragment

class MainPresenter(private val mainView: MainView) {


    fun setupTabView() {
        val tabAdapter = TabAdapter(mainView.getTabContext(), mainView.getTabFragmentManager())

        tabAdapter.addFragment(
            MoviesFragment(),
            "Home", R.drawable.ic_home_black_24dp
        )
        tabAdapter.addFragment(
            FavoritesFragment(),
            "Favorites", R.drawable.ic_favorite_black_24dp
        )
        mainView.setupTabView(tabAdapter, getOnPageChangeListener())

    }

    private fun getOnPageChangeListener(): ViewPager.OnPageChangeListener {
        return object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                mainView.highLightCurrentItem(position)
            }

        }
    }


}