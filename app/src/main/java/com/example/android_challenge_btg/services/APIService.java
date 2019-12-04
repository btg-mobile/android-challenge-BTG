package com.example.android_challenge_btg.services;

import android.content.Context;

import com.example.android_challenge_btg.BuildConfig;
import com.example.android_challenge_btg.callbacks.APIServiceGenreCallback;
import com.example.android_challenge_btg.callbacks.APIServiceMovieCallback;
import com.example.android_challenge_btg.callbacks.HttpServiceCallback;
import com.example.android_challenge_btg.models.Movie;
import com.example.android_challenge_btg.models.responses.GenreResponse;
import com.example.android_challenge_btg.models.responses.MovieInstanceCreator;
import com.example.android_challenge_btg.models.responses.MoviesResponse;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class APIService {

    private int moviesPage = 1;
    private HttpService httpService;

    private final String host = "https://api.themoviedb.org/3";
    private final String popularMovieURL = host + "/movie/popular?api_key=" + BuildConfig.API_KEY + "&page=" + moviesPage;
    private final String genreMovieURL = host + "/genre/movie/list?api_key=" + BuildConfig.API_KEY;

    private Gson gson;


    public APIService(Context context) {
        this.moviesPage = 1;
        this.httpService = HttpService.getInstance(context);
        this.gson = new Gson();
    }

    public void requestGenres(APIServiceGenreCallback genreCallback) {
        try {
            this.httpService.get(genreMovieURL, new HttpServiceCallback() {
                @Override
                public void onSuccess(String response) {
                    GenreResponse genreResponse = gson.fromJson(response, GenreResponse.class);
                    genreCallback.onSuccess(genreResponse.getGenres());
                }

                @Override
                public void onError(String message) {
                    genreCallback.onError(message);
                }
            });
        } catch (Exception e) {
            genreCallback.onError("Error on decrypt response: " + e.getMessage());
        }
    }

    public void requestMovies(APIServiceMovieCallback movieCallback) {
        try {
            this.httpService.get(popularMovieURL, new HttpServiceCallback() {
                @Override
                public void onSuccess(String response) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Movie.class, new MovieInstanceCreator());
                    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                    MoviesResponse moviesResponse = gsonBuilder.create().fromJson(response, MoviesResponse.class);
                    movieCallback.onSuccess(moviesResponse.getResults());
                }

                @Override
                public void onError(String message) {
                    movieCallback.onError(message);
                }
            });
        } catch (Exception e) {
            movieCallback.onError("Error on decrypt response: " + e.getMessage());
        }
    }


}
