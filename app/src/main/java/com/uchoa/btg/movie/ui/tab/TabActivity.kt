package com.uchoa.btg.movie.ui.tab

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import com.uchoa.btg.movie.R
import kotlinx.android.synthetic.main.activity_tab.*

class TabActivity : AppCompatActivity() {

    private var menuItem: MenuItem? = null
    private var searchView: SearchView? = null
    private var tabAdapter: TabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        setUpTabs()
    }

    private fun setUpTabs() {
        tabAdapter = TabAdapter(applicationContext, supportFragmentManager)
        viewPager.adapter = tabAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                setupTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        movieTabs.post { movieTabs.setupWithViewPager(viewPager) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.search_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        menuItem = menu.findItem(R.id.action_search)

        if (menuItem != null) {
            searchView = MenuItemCompat.getActionView(menuItem!!) as SearchView
            if (searchView != null) {
                searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(query: String): Boolean {
                        tabAdapter?.search(query)
                        return false
                    }
                })

                searchView!!.queryHint = getString(R.string.tab_search_hint)
            }
        }
        return true
    }

    fun setupTab(position: Int) {
        if (menuItem != null && searchView != null) {
            when (position) {
                0 -> {
                    menuItem!!.isVisible = true
                    searchView!!.visibility = View.VISIBLE
                }
                1 -> {
                    menuItem!!.isVisible = true
                    searchView!!.visibility = View.VISIBLE
                }
            }
        }
    }
}