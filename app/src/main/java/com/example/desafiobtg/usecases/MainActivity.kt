package com.example.desafiobtg.usecases

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.desafiobtg.R
import com.example.desafiobtg.utils.ActivityUtils
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity @Inject constructor(): DaggerAppCompatActivity(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var injectedFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainFragment: MainFragment? = supportFragmentManager.findFragmentById(R.id.fragment_container) as MainFragment?

        if (mainFragment == null) {
            mainFragment = injectedFragment
            ActivityUtils.addFragmentToActivity(supportFragmentManager, mainFragment, R.id.fragment_container)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.queryHint = getString(R.string.search_view_query_hint)

        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        injectedFragment.onQueryTextChange(query)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }
}
