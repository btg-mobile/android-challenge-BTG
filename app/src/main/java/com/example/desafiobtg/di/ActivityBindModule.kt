package com.example.desafiobtg.di

import com.example.desafiobtg.di.qualifiers.ActivityScoped
import com.example.desafiobtg.usecases.MainActivity
import com.example.desafiobtg.usecases.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity

}