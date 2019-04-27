package com.example.desafiobtg.usecases

import android.os.Bundle
import com.example.desafiobtg.R
import com.example.desafiobtg.utils.ActivityUtils
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity @Inject constructor(): DaggerAppCompatActivity() {

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
}
