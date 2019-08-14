package com.androidchallengebtg.activities.movies.controllers;

import android.util.Log;

import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MoviesController {

    private Listener listener;

    public interface Listener{
        void onSuccess(JSONObject response);
        void onError(String message);
    }

    public MoviesController(Listener listener) {
        this.listener = listener;
    }

    public void getListMovies(){
        Connection connection = new Connection();
        connection.getMovies(new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                listener.onSuccess(response);
            }

            @Override
            public void onError(JSONObject error) {
                try {
                    Log.e("onError",error.toString());
                    String message = error.getString("status_message");
                    listener.onError(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
