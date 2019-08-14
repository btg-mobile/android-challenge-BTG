package com.androidchallengebtg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.androidchallengebtg.helpers.Connection;
import com.androidchallengebtg.helpers.ConnectionListener;
import com.androidchallengebtg.helpers.ConnectionQueue;

import org.json.JSONException;
import org.json.JSONObject;

/*
user: btgchallenge
email: raphaelrocha86+btg@gmail.com
pass: 2405
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("LOG", "main");
        ConnectionQueue.getINSTANCE().startQueue(this);

        String url = "https://api.teewa.com.br/";
        new Connection().request(url, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    String message = jsonObject.getString("message");
                    Log.w("LOG", message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                Log.e("LOG", volleyError.toString());
            }
        });
    }
}
