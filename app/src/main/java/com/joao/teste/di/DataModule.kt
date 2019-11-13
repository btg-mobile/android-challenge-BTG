package com.joao.teste.di

import com.joao.data.client.ApiClient.makeService
import com.joao.data.source.remote.MoviesDataSource
import com.joao.data.source.remote.MoviesDataSourceImpl
import com.joao.data.source.remote.service.MoviesService
import com.joao.data.repository.MoviesRepositoryImpl
import com.joao.data.source.local.FavoritesDataSource
import com.joao.data.source.local.FavoritesDataSourceImpl
import com.joao.domain.repository.MoviesRepository
import org.koin.dsl.module

object DataModule {
    val serviceModules = module {
        single { makeService<MoviesService>() }
    }

    val dataModules = module {

        single<MoviesDataSource> {
            MoviesDataSourceImpl(get(), get())
        }
        single<FavoritesDataSource> {
            FavoritesDataSourceImpl(get())
        }
        single<MoviesRepository> { MoviesRepositoryImpl(get(), get()) }
    }

}