package com.joao.teste.component

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.joao.teste.presentation.FavoritesFragment
import com.joao.teste.presentation.HomeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0-> HomeFragment.newInstance()
            else -> FavoritesFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}