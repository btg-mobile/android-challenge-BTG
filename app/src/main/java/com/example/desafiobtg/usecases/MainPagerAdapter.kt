package com.example.desafiobtg.usecases

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class MainPagerAdapter constructor(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList: MutableList<Fragment> = arrayListOf()
    private val mFragmentTitle: MutableList<String> = arrayListOf()

    fun addPage(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitle.add(title)
    }

    override fun getItem(position: Int) = mFragmentList[position]
    override fun getCount() = mFragmentList.size
    override fun getPageTitle(position: Int) = mFragmentTitle[position]
}