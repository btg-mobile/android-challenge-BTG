package com.example.desafiobtg.di

import android.app.Application
import com.example.desafiobtg.AppController
import com.example.desafiobtg.data.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        ApplicationModule::class,
        RepositoryModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBindModule::class,
        NetworkModule::class,
        DatabaseModule::class]
)
@Singleton
interface AppComponent: AndroidInjector<DaggerApplication> {

    fun inject(application: AppController)

    override fun inject(instance: DaggerApplication?)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}