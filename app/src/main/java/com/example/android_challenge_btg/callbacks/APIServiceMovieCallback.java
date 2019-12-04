package com.example.android_challenge_btg.callbacks;

import com.example.android_challenge_btg.models.Movie;

import java.util.List;

public interface APIServiceMovieCallback {
    void onSuccess(List<Movie> movies);
    void onError(String message);

}
