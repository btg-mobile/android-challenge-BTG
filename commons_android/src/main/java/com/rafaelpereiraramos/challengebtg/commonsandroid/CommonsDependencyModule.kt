package com.rafaelpereiraramos.challengebtg.commonsandroid

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

class CommonsDependencyModule {

    companion object {
        val commonsNetworkDependency = module {
            single { ConnectivityHelper.getInstance(androidApplication()) }
        }
    }
}