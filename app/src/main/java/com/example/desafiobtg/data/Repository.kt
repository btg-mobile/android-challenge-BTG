package com.example.desafiobtg.data

import android.content.Context
import com.example.desafiobtg.data.local.LocalDataSource
import com.example.desafiobtg.data.remote.RemoteDataSource
import com.example.desafiobtg.di.qualifiers.utils.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource,
                                     private val localDataSource: LocalDataSource,
                                     @ApplicationContext private val context: Context) {



}