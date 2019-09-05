package com.curymorais.moviedbapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.curymorais.moviedbapp.favoriteMovies.FavoriteMoviesFragment
import com.curymorais.moviedbapp.popularMovies.PopularMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    val TAG = MainActivity::class.java.simpleName
    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout_constraint)

        initComponents()
        setComponentListeners()
        loadFragment(PopularMoviesFragment())
    }

    fun initComponents(){
        bottomNavigationView =  findViewById(R.id.navigationView)
    }

    fun setComponentListeners(){
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var frag : Fragment? = null
        when (p0.itemId) {
            R.id.popular_movies -> {
                Log.i(TAG, "localizator tool started")
                frag = PopularMoviesFragment()
                return loadFragment(frag)
            }
            R.id.favorites_movies -> {
                Log.i(TAG, "math tool started")
                frag = FavoriteMoviesFragment()
                return loadFragment(frag)
            }
        }
        return false
    }

    private fun loadFragment(fragment : Fragment?) : Boolean{
        if (fragment != null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return  true
        }
        return false
    }


}