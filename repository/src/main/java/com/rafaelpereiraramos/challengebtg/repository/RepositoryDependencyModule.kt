package com.rafaelpereiraramos.challengebtg.repository

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.rafaelpereiraramos.challengebtg.commonsandroid.CommonsDependencyModule
import com.rafaelpereiraramos.challengebtg.repository.api.TmdbService
import com.rafaelpereiraramos.challengebtg.repository.db.AppDatabase
import com.rafaelpereiraramos.challengebtg.repository.db.dao.MovieDao
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
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

            single {
                Room.databaseBuilder(
                    androidApplication(),
                    AppDatabase::class.java,
                    "challenge-btg"
                ).build()
            }

            single {
                get<AppDatabase>().movieDao()
            }

            single {
                get<AppDatabase>().genreDao()
            }

            single {
                get<AppDatabase>().movieGenreDao()
            }

            single<AppRepository> {
                AppRepositoryImpl(get(), get(), get(), get(), get())
            }
        }.plus(CommonsDependencyModule.commonsNetworkDependency)
    }
}