package com.desafio.marvelapp.detailCharacter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.desafio.marvelapp.R
import com.desafio.marvelapp.base.BaseActivity
import com.desafio.marvelapp.util.MarvelConstant
import com.desafio.marvelapp.util.MarvelUtil

class DetailCharacterActivity : BaseActivity() {

    // Problem kodein version 4.1.0
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val bundle = this.intent.extras
        val detailFragment = DetailCharacterFragment()
        detailFragment.arguments = bundle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_top))

        if (savedInstanceState == null) {
            MarvelUtil.showFragment(this, R.id.content_main, detailFragment, false, MarvelConstant.CHARACTER)
        }

    }
}
