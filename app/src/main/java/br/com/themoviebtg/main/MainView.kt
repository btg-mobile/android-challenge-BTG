package br.com.themoviebtg.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager

interface MainView {
    fun setupToolBar()
    fun getTabContext(): Context
    fun getTabFragmentManager(): FragmentManager

    fun setupTabView(tabAdapter: TabAdapter, listener: ViewPager.OnPageChangeListener)
    fun highLightCurrentItem(position: Int)
}