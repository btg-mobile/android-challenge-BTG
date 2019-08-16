package com.androidchallengebtg.activities.movieDetail;

import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.Tools;
import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class MovieDetailController {

    interface MovieChangeLister{
        void onSuccess();
        void onError(String message);
    }

    private JSONObject movie;
    private int movieId;
    private Connection connection;
    private boolean favorite;

    MovieDetailController(JSONObject movie) {
        connection = new Connection();
        setMovie(movie);
    }

    String getTitle(){
        String title = null;
        try {
            title = this.movie.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return title;
    }

    String getOverview(){
        String overview = null;
        try {
            overview = this.movie.getString("overview");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return overview;
    }

    String getVoteAverage(){
        String voteAverage = null;
        try {
            voteAverage = this.movie.getString("vote_average");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return voteAverage;
    }

    String getBackdropUrl(){
        String baseImageUrl = Tools.getBaseImageUrl("original");
        String urlBackdrop = null;
        try {
            urlBackdrop = baseImageUrl+movie.getString("backdrop_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return urlBackdrop;
    }

    String getGenres(){
        StringBuilder genresBuilder = new StringBuilder();
        try {
            JSONArray genresArray = movie.getJSONArray("genres");
            for(int i = 0; i<=genresArray.length()-1; i++){
                JSONObject genre = new JSONObject(genresArray.get(i).toString());
                if(i == 0){
                    genresBuilder = new StringBuilder(genre.getString("name"));
                }else{
                    genresBuilder.append(" ").append(genre.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return genresBuilder.toString();
    }

    boolean isFavorite(){
        return this.favorite;
    }


    private void setMovie(JSONObject movie) {
        this.movie = movie;
        try {
            this.movieId = movie.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMovieStatus(JSONObject movieStatus) {
        this.favorite = false;
        try {
            this.favorite = movieStatus.getBoolean("favorite");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void responseError(JSONObject error,MovieChangeLister movieChangeLister){
        String message = ApplicationBTG.getContext().getString(R.string.unknow_error);
        try {
            message = error.getString("status_message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        movieChangeLister.onError(message);
    }

    void markAsFavorite(final MovieChangeLister movieChangeLister){
        connection.markAsFavorite("movie",this.movieId, !this.favorite, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                movieChangeLister.onSuccess();
            }

            @Override
            public void onError(JSONObject error) {
                responseError(error,movieChangeLister);
            }
        });
    }
    void getMovieStatus(final MovieChangeLister movieChangeLister){
        connection.getMovieAccountState(this.movieId, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                setMovieStatus(response);
                movieChangeLister.onSuccess();
            }

            @Override
            public void onError(JSONObject error) {
                responseError(error,movieChangeLister);
            }
        });
    }

    void getMovie(final MovieChangeLister movieChangeLister){
        connection.getMovie(this.movieId, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                setMovie(response);
                movieChangeLister.onSuccess();
            }

            @Override
            public void onError(JSONObject error) {
                responseError(error,movieChangeLister);
            }
        });
    }
}
