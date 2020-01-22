package com.rafaelpereiraramos.challengebtg.repository

import com.google.gson.GsonBuilder
import com.rafaelpereiraramos.challengebtg.commonsandroid.CommonsDependencyModule
import com.rafaelpereiraramos.challengebtg.repository.api.TmdbService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryDependencyModule {

    companion object {
        val repositoryDependencyModule = module {
            single {
                OkHttpClient
                    .Builder()
                    .build()
            }

            single {
                GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create()
            }

            single {
                Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(get()))
                    .client(get())
                    .build()
                    .create(TmdbService::class.java)
            }

            single<AppRepository> {
                AppRepositoryImpl(get(), get())
            }
        }.plus(CommonsDependencyModule.commonsNetworkDependency)
    }
}