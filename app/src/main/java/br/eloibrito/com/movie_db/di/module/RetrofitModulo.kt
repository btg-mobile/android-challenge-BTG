package br.eloibrito.com.movie_db.di.module

import android.util.Log
import br.eloibrito.com.movie_db.BuildConfig
import br.eloibrito.com.movie_db.data.network.ApiRetrofit
import br.eloibrito.com.movie_db.data.network.EndPoint
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RetrofitModulo {

    @Provides
    @Singleton
    @Named("interpo-retrofit")
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Named("time-retro")
    fun getTimeOut(): Int {
        return 240
    }

    /*
    Header Initialization
    */
    @Provides
    @Named("retro-header")
    fun getHeaders(): HashMap<String, String> {
        val params = HashMap<String, String>()
        params.put("Content-Type", "application/json")
        return params
    }

    @Provides
    fun getApiInterface(retrofit: Retrofit): ApiRetrofit {
        return retrofit.create(ApiRetrofit::class.java)
    }

    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(EndPoint.url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getokHttpClient(getHttpLoggingInterceptor(), getHeaders(), getTimeOut()))
            .build()
    }

    @Provides
    fun getokHttpClient(
         httpLoggingInterceptor: HttpLoggingInterceptor, headers: HashMap<String, String>,  timeout: Int
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request()
                val builder = request.newBuilder()

                if (headers != null && headers.size > 0) {
                    for ((key, value) in headers) {
                        builder.addHeader(key, value)
                        Log.e(key, value)
                    }
                }
                chain.proceed(builder.build())
            }
            .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
            .build()
    }

}