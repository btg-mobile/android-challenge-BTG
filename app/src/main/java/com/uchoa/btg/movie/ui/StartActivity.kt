package com.uchoa.btg.movie.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.uchoa.btg.movie.R
import com.uchoa.btg.movie.ui.tab.TabActivity
import com.uchoa.btg.movie.utils.Constants
import com.uchoa.btg.movie.utils.FontStyleUtil
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        startAnimations()
        FontStyleUtil.applyFontStyle(this, splashTitle, Constants.FONT_STYLE_STAR_WARS)
    }

    private fun startAnimations() {
        val splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash)
        splashAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                callTabActivity()
            }

        })
        splashImage.startAnimation(splashAnim)

        val titleAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        titleAnim.duration = 2000
        splashTitle.startAnimation(titleAnim)
    }

    private fun callTabActivity() {
        val intent = Intent(this, TabActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}