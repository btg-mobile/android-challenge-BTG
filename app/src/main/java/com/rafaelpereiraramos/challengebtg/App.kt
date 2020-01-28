package com.rafaelpereiraramos.challengebtg

import android.app.Application
import com.rafaelpereiraramos.challengebtg.view.ViewDependencyModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(ViewDependencyModule.viewModelModule)
        }
    }
}