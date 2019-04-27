package com.example.desafiobtg.data

import com.example.desafiobtg.data.local.LocalDataSource
import com.example.desafiobtg.data.local.LocalDataSourceImpl
import com.example.desafiobtg.data.remote.RemoteDataSource
import com.example.desafiobtg.data.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource

    @Singleton
    @Binds
    abstract fun provideLocalDataSource(localDataSource: LocalDataSourceImpl): LocalDataSource
}