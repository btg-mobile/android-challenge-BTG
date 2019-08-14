package com.androidchallengebtg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;
import com.androidchallengebtg.helpers.connection.ConnectionQueue;

import org.json.JSONException;
import org.json.JSONObject;

/*
user: btgchallenge
email: raphaelrocha86+btg@gmail.com
pass: 2405
 */

public class MainActivity extends AppCompatActivity {

    private EditText mInputLogin;
    private EditText mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputLogin = findViewById(R.id.input_login);
        mInputPassword = findViewById(R.id.input_password);

        mInputLogin.setText("raphaelrocha86+btg@gmail.com");
        mInputPassword.setText("2405");

        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("onResume", "main");


    }

    private void performLogin () {

        String username = mInputLogin.getText().toString().trim();
        String password = mInputPassword.getText().toString().trim();

        /*
        Aborta se login ou senha estiverem vazios
         */
        if(username.length() <1 || password.length() <1){
            Toast.makeText(this,getString(R.string.empty_login_or_pass),Toast.LENGTH_LONG).show();
            return;
        }

        Connection connection = new Connection();

        /*
        Pede request token da api
         */
        connection.createRequestToken(new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.w("LOG", response.toString());
                try {
                    String requestToken = response.getString("request_token");
                    Log.w("onSuccess", requestToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ApplicationBTG.getContext(),getString(R.string.unknow_error),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(JSONObject error) {

                try {
                    Log.e("onError",error.toString());
                    String message = error.getString("status_message");
                    Toast.makeText(ApplicationBTG.getContext(),message,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
