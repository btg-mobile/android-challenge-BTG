package com.joao.teste.presentation

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.joao.data.util.Shared
import com.joao.data.util.remove
import com.joao.domain.entity.MoviesItem
import com.joao.teste.R
import com.joao.teste.component.ViewPagerAdapter
import com.joao.teste.presentation.base.BaseAdapter
import com.joao.teste.util.DialogFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    val moviesViewModel: MainViewModel by viewModel()

    var adapterHome: BaseAdapter<MoviesItem?>? = null
    var adapterFavorites: BaseAdapter<MoviesItem?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(moviesViewModel)
        supportActionBar?.title = "Filmes"
        val loading = DialogFactory.getLoading(this)
        nav_fragment.adapter = ViewPagerAdapter(supportFragmentManager)
        setUpTabs()

        moviesViewModel.loading.observe(this, Observer {
            if (it)
                loading.show()
            else
                loading.dismiss()
        })

        moviesViewModel.error.observe(this, Observer {
            DialogFactory.getDialogError(this, it).show()
        })
    }

    private fun setUpTabs() {
        tab_home.setupWithViewPager(nav_fragment)
        tab_home.getTabAt(0)?.text = "Principal"
        tab_home.getTabAt(1)?.text = "Favoritos"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val myActionMenuItem = menu.findItem(R.id.action_search)
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    adapterFavorites?.filter?.filter("")
                    adapterHome?.filter?.filter("")
                } else {
                    adapterHome?.filter?.filter(newText)
                    adapterFavorites?.filter?.filter(newText)
                }
                return true
            }
        })

        return true
    }
}
