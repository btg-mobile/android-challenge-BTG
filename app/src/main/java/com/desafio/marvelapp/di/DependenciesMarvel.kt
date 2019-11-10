package com.desafio.marvelapp.di

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.lazy
import com.desafio.marvelapp.data.repository.di.DataManagerModule
import com.desafio.marvelapp.data.repository.di.LocalStorageModule

class DependenciesMarvel(private val application: Application) {

    val dependenciesGraph = Kodein.lazy {
        import(LocalStorageModule(application).dependenciesKodein)
        import(DataManagerModule().dependenciesKodein)
    }

}