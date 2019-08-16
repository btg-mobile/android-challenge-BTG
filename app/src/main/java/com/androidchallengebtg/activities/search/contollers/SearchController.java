package com.androidchallengebtg.activities.search.contollers;

import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchController {

    private Listener listener;

    public interface Listener{
        void onSuccess(JSONObject response);
        void onError(String message);
    }

    public interface FavoriteListener{
        void onSuccess(JSONObject response);
        void onError(String message);
    }

    public SearchController(Listener listener) {
        this.listener = listener;
    }

    private void responseError(JSONObject error){
        String message = ApplicationBTG.getContext().getString(R.string.unknow_error);
        try {
            message = error.getString("status_message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onError(message);
    }

    public void search(String query, int page){

        if(query == null || query.length() < 1){
            return;
        }

        if(page < 1) {
            page = 1;
        }

        Connection connection = new Connection();
        connection.search(query, page, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                listener.onSuccess(response);
            }

            @Override
            public void onError(JSONObject error) {
                responseError(error);
            }
        });
    }

    public void markAsFavorite(int movieId, boolean favorite, final FavoriteListener favoriteListener){
        Connection connection = new Connection();
        connection.markAsFavorite("movie",movieId, favorite, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                favoriteListener.onSuccess(response);
            }

            @Override
            public void onError(JSONObject error) {
                String message = ApplicationBTG.getContext().getString(R.string.unknow_error);
                try {
                    message = error.getString("status_message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                favoriteListener.onError(message);
            }
        });
    }
}
