package com.example.desafiobtg.usecases

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainPagerAdapter constructor(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList: MutableList<Fragment> = arrayListOf()

    fun addPage(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun fragments() = mFragmentList.toList()
}