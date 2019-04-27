package com.example.desafiobtg.di

import android.app.Application
import android.content.Context
import com.example.desafiobtg.di.qualifiers.utils.ApplicationContext
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    //expose Application as an injectable context
    @ApplicationContext
    @Binds
    internal abstract fun bindContext(application: Application): Context
}