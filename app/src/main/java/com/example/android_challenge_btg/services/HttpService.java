package com.example.android_challenge_btg.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android_challenge_btg.callbacks.HttpServiceCallback;

public class HttpService {

    private RequestQueue requestQueue;
    private static HttpService instance;


    private HttpService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    static HttpService getInstance(Context context) {
        if(instance == null) {
            instance = new HttpService(context);
        }

        return instance;
    }

    public void get(String url, HttpServiceCallback callback) {
        request(url, Request.Method.GET, callback);
    }

    public void post(String url, HttpServiceCallback callback) {
        request(url, Request.Method.POST, callback);

    }

    private void request(String url, int method, HttpServiceCallback callback) {
        StringRequest request =
                new StringRequest(method, url, callback::onSuccess, error -> callback.onError(error.getMessage()));

        requestQueue.add(request);
    }

}
