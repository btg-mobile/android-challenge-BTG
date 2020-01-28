package com.rafaelpereiraramos.challengebtg.view.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchFragmentPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val pages = mutableListOf<Fragment>()

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]
}