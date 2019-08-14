package com.androidchallengebtg.helpers.connection;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;

import org.json.JSONException;
import org.json.JSONObject;

public class Connection {

    private Context context;
    private String baseUrl;
    private String apiKey;

    public Connection() {
        context =  ApplicationBTG.getContext();
        baseUrl = context.getString(R.string.tmdb_base_url);
        apiKey = "?api_key="+context.getString(R.string.tmdb_api_key);
    }

    private void get(String url, final ConnectionListener connectionListener){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        connectionListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        JSONObject error = null;
                        try {
                            error = new JSONObject(new String(volleyError.networkResponse.data));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        connectionListener.onError(error);
                    }
                });

        ConnectionQueue.getINSTANCE().addQueue(jsonObjectRequest);
    }

    public void request (String route, final ConnectionListener connectionListener){
        String url = baseUrl+route+apiKey;
        Log.d("vai chamar", url);
        get(url,connectionListener);
    }

    public void createRequestToken (final ConnectionListener connectionListener){
        String route = context.getString(R.string.authentication_token_new);
        String url = baseUrl+route+apiKey;
        Log.d("vai chamar", url);
        get(url,connectionListener);
    }
}
