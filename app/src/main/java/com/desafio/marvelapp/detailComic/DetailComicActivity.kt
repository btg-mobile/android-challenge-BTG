package com.desafio.marvelapp.detailComic

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.desafio.marvelapp.R
import com.desafio.marvelapp.base.BaseActivity
import com.desafio.marvelapp.util.MarvelConstant
import com.desafio.marvelapp.util.MarvelUtil

class DetailComicActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val bundle = this.intent.extras
        val detailFragment = DetailComicFragment()
        detailFragment.arguments = bundle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_top))

        if (savedInstanceState == null) {
            MarvelUtil.showFragment(this, R.id.content_main, detailFragment, false, MarvelConstant.CHARACTER)
        }

    }
}
