package com.dacruzl2.btgdesafio.model.impl;

import android.content.Context;

import com.dacruzl2.btgdesafio.model.inter.IRetrofitManager;
import com.dacruzl2.btgdesafio.model.inter.MovieService;
import com.dacruzl2.btgdesafio.model.pojos.RootGenres;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Movie;
import com.dacruzl2.btgdesafio.model.pojos.pojoteste.Root;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager implements IRetrofitManager {

    public final String URL_BASE = "https://api.themoviedb.org/3/";
    public static final String API_KEY = "69bfa377432355ee8eb4376f78ca7c81";

    public RetrofitManager() {
    }

    private MovieService movieService;

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control",
                        String.format("max-age=%d", 60))
                .build();
    };

    public void conn(Context context) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File httpCacheDirectory = new File(context.getCacheDir(), "offlineCache");
        //10 MB
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(provideOfflineCacheInterceptor())
                .addInterceptor(provideCacheInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient)
                .baseUrl(URL_BASE)
                .build();

        movieService = retrofit.create(MovieService.class);

    }

    public static Interceptor provideCacheInterceptor() {

        return chain -> {
            Request request = chain.request();
            Response originalResponse = chain.proceed(request);
            String cacheControl = originalResponse.header("Cache-Control");

            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")) {

                CacheControl cc = new CacheControl.Builder()
                        .maxStale(1, TimeUnit.DAYS)
                        .build();

                    /*return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-stale=" + 60 * 60 * 24)
                            .build();*/

                request = request.newBuilder()
                        .cacheControl(cc)
                        .build();

                return chain.proceed(request);

            } else {
                return originalResponse;
            }
        };

    }

    public static Interceptor provideOfflineCacheInterceptor() {

        return chain -> {
            try {
                return chain.proceed(chain.request());
            } catch (Exception e) {


                CacheControl cacheControl = new CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(1, TimeUnit.DAYS)
                        .build();
                Request offlineRequest = chain.request().newBuilder()
                        .cacheControl(cacheControl)
                        .header("Cache-Control", "public, only-if-cached")
                        .removeHeader("Pragma")
                        .build();
                return chain.proceed(offlineRequest);
            }
        };
    }

    @Override
    public Call<Root> retrievePopularMovies(String apiKey, int page, String language) {
        return movieService.getPopularMovies(apiKey, page, language);
    }

    @Override
    public Call<Root> retrieveMoviesBySearch(String apiKey, int page, String query) {
        return movieService.getMoviesByQuery(apiKey, page, query);
    }

    @Override
    public Call<Movie> getMovie(String apiKey, int id, String language) {
        return movieService.getMovie(id, apiKey, language);
    }

    @Override
    public Call<RootGenres> getGenres(String apiKey, String language) {
        return movieService.getGenre(apiKey, language);
    }
}
