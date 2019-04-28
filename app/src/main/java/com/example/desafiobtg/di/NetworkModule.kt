package com.example.desafiobtg.di

import android.text.TextUtils
import com.example.desafiobtg.BuildConfig
import com.example.desafiobtg.api.MovieApi
import com.example.desafiobtg.db.room.DataUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.CipherSuite
import okhttp3.TlsVersion
import okhttp3.ConnectionSpec



@Module
class NetworkModule {

    val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
    val READ_TIMEOUT = "READ_TIMEOUT"
    val WRITE_TIMEOUT = "WRITE_TIMEOUT"
    val DEFAULT_CONNECT_TIMEOUT = 2000
    val DEFAULT_WRITE_TIMEOUT = 10000
    val DEFAULT_READ_TIMEOUT = 10000

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson): Retrofit {

        val timeoutInterceptor = Interceptor { chain ->
            val request = chain.request()

            var connectTimeout = chain.connectTimeoutMillis()
            var readTimeout = chain.readTimeoutMillis()
            var writeTimeout = chain.writeTimeoutMillis()

            val connectNew = request.header(CONNECT_TIMEOUT)
            val readNew = request.header(READ_TIMEOUT)
            val writeNew = request.header(WRITE_TIMEOUT)

            if (!TextUtils.isEmpty(connectNew)) {
                connectTimeout = Integer.valueOf(connectNew!!)
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.valueOf(readNew!!)
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.valueOf(writeNew!!)
            }

            chain
                .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .proceed(request)
        }

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(timeoutInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(BuildConfig.serviceAddress)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create<MovieApi>(MovieApi::class.java)
    }

    @Provides
    @Singleton
    internal fun providesGson(): Gson {
        return DataUtils.createGsonBuilder()
    }
}