package com.androidchallengebtg.activities.movieDetail;

import org.json.JSONException;
import org.json.JSONObject;

class MovieDetailController {

    private JSONObject movie;
    private JSONObject movieStatus;
    private int movieId;

    MovieDetailController(JSONObject movie) {
        setMovie(movie);
    }

    void setMovie(JSONObject movie) {
        this.movie = movie;
        try {
            this.movieId = movie.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setMovieStatus(JSONObject movieStatus) {
        this.movieStatus = movieStatus;

    }

    JSONObject getMovie() {
        return movie;
    }

    int getMovieId() {
        return movieId;
    }

    boolean isFavorite(){
        boolean favorite = false;
        try {
            favorite = this.movieStatus.getBoolean("favorite");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favorite;
    }
}
