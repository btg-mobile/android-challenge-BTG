package com.androidchallengebtg.helpers.connection;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.storage.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Connection {

    private Context context;
    private String baseUrl;
    private String apiKey;

    public Connection() {
        context =  ApplicationBTG.getContext();
        baseUrl = context.getString(R.string.tmdb_base_url);
        apiKey = "?api_key="+context.getString(R.string.tmdb_api_key);
    }

    private void request(int method, String url, JSONObject body, final ConnectionListener connectionListener){

        Log.w("request url",url);
        Log.w("request body",body != null ? body.toString() : "");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (method, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        connectionListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        JSONObject error = null;
                        try {
                            Log.e("volleyError",new String(volleyError.networkResponse.data));
                            error = new JSONObject(new String(volleyError.networkResponse.data));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        connectionListener.onError(error);
                    }
                });

        ConnectionQueue.getINSTANCE().addQueue(jsonObjectRequest);
    }

    public void createRequestToken (final ConnectionListener connectionListener){
        String route = context.getString(R.string.authentication_token_new);
        String url = baseUrl+route+apiKey;
        request(Request.Method.GET,url,null,connectionListener);
    }

    public void validateRequestToken (String username, String password, String requestToken, ConnectionListener connectionListener){
        Map<String, String > map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        map.put("request_token",requestToken);
        JSONObject body = new JSONObject(map);
        String url = baseUrl+ApplicationBTG.getContext().getString(R.string.authentication_token_validate_with_login)+apiKey;
        request(Request.Method.POST,url,body,connectionListener);
    }

    public void createSession(String requestToken, ConnectionListener connectionListener){
        String route = context.getString(R.string.authentication_session_new);
        String url = baseUrl+route+apiKey;
        Map<String, String > map = new HashMap<>();
        map.put("request_token",requestToken);
        JSONObject body = new JSONObject(map);
        request(Request.Method.POST,url,body,connectionListener);
    }

    public void getAccountDetails(ConnectionListener connectionListener){
        String sessionId = "&session_id="+PrefManager.getINSTANCE().getSessionId();
        String route = context.getString(R.string.account);
        String url = baseUrl+route+apiKey+sessionId;
        request(Request.Method.GET,url,null,connectionListener);
    }

    public void getMovies(int page, ConnectionListener connectionListener){
        String route = context.getString(R.string.movie_popular);
        String url = baseUrl+route+apiKey+"%&page="+page;
        request(Request.Method.GET,url,null,connectionListener);
    }

    public void getFavoriteMovies(int page, ConnectionListener connectionListener){
        try {
            String route = context.getString(R.string.favorite_movies);
            JSONObject user = PrefManager.getINSTANCE().getUser();
            int accountId = user.getInt("id");
            route = route.replace("{account_id}",String.valueOf(accountId));
            String sessionId = "&session_id="+PrefManager.getINSTANCE().getSessionId();
            String url = baseUrl+route+apiKey+"&page="+page+sessionId;
            request(Request.Method.GET,url,null,connectionListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
