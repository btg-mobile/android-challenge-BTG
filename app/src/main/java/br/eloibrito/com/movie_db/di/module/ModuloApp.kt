package br.eloibrito.com.movie_db.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuloApp(var application: Application) {

    var applicationInst: Application = application

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return applicationInst
    }
}