package com.joao.teste

import android.app.Application
import com.joao.teste.di.AppComponent.getAllModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class MovieApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidLogger()
            androidContext(this@MovieApplication)
            modules(getAllModules())
        }
    }
}