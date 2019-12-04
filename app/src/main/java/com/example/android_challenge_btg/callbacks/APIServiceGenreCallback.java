package com.example.android_challenge_btg.callbacks;

import com.example.android_challenge_btg.models.Genre;

import java.util.List;

public interface APIServiceGenreCallback {
    void onSuccess(List<Genre> genres);
    void onError(String message);

}
