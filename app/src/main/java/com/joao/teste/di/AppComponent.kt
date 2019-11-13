package com.joao.teste.di

import com.joao.teste.di.DataModule.dataModules
import com.joao.teste.di.DataModule.serviceModules
import com.joao.teste.di.DomainModule.domainModules
import com.joao.teste.di.ViewModelModule.viewModelModules
import org.koin.core.module.Module

object AppComponent {

    fun getAllModules(): List<Module> =
        listOf(*getViewModelModules(), *getDomainModules(), *getDataModules())

    private fun getViewModelModules(): Array<Module> {
        return arrayOf(viewModelModules)
    }

    private fun getDomainModules(): Array<Module> {
        return arrayOf(domainModules)
    }

    private fun getDataModules(): Array<Module> {
        return arrayOf(serviceModules, dataModules)
    }
}