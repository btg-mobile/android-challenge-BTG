package com.example.desafiobtg.usecases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiobtg.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MainFragment @Inject constructor(): DaggerFragment(), MainContract.View {

    @Inject
    lateinit var mPresenter: MainContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.frag_main, container, false)
        mPresenter.takeView(this)
        return v
    }

    override fun onDestroy() {
        mPresenter.dropView()
        super.onDestroy()
    }

}