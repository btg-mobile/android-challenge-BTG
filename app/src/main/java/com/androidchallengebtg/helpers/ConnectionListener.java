package com.androidchallengebtg.helpers;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ConnectionListener {

    void onSuccess (JSONObject jsonObject);
    void onError (VolleyError volleyError);
}
