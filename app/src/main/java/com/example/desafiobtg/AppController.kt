package com.example.desafiobtg

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.support.multidex.MultiDex
import com.example.desafiobtg.di.DaggerAppComponent
import com.example.desafiobtg.di.AppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class AppController: DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    fun getComponent(): AppComponent {
        return appComponent
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {

        fun runOnMain(r: () -> Unit) {
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post { r() }
        }

        fun runOnBG(r: () -> Unit) {
            AsyncTask.execute { r() }
        }
    }
}