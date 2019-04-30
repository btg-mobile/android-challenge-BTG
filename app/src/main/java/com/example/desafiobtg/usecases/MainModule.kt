package com.example.desafiobtg.usecases

import com.example.desafiobtg.di.qualifiers.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun mainView(): MainFragment

}