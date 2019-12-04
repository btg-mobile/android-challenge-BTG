package com.example.android_challenge_btg.models.responses;

import com.example.android_challenge_btg.models.Genre;

import java.util.List;

public class GenreResponse {
    private List<Genre> genres;

    public GenreResponse(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
