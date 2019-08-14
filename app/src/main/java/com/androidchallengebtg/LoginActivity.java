package com.androidchallengebtg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;
import com.androidchallengebtg.helpers.storage.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

/*
user: btgchallenge
email: raphaelrocha86+btg@gmail.com
pass: 2405
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mInputLogin;
    private EditText mInputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputLogin = findViewById(R.id.input_login);
        mInputPassword = findViewById(R.id.input_password);

        mInputLogin.setText("btgchallenge");
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

    /*
    Valida os campos do formuário de login
     */
    private void performLogin () {

        final String username = mInputLogin.getText().toString().trim();
        final String password = mInputPassword.getText().toString().trim();

        /*
        Aborta se login ou senha estiverem vazios
         */
        if(username.length() <1 || password.length() <1){
            Toast.makeText(this,getString(R.string.empty_login_or_pass),Toast.LENGTH_LONG).show();
            return;
        }

        createRequestToken(username,password);

    }

    /*
    Usa o api token para criar um request token
     */
    private void createRequestToken(final String username, final String password){

        Connection connection = new Connection();

        connection.createRequestToken(new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.w("onSuccess", response.toString());
                try {
                    String requestToken = response.getString("request_token");
                    PrefManager.getINSTANCE().saveRequestToken(requestToken);
                    validateRequestToken(username, password, requestToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject error) {

            }
        });
    }

    /*
    Vaida o request token mediante login e senha
     */
    private void validateRequestToken(String username, String password, String requestToken){

        Connection connection = new Connection();

        connection.validateRequestToken(username, password, requestToken, new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.w("LOG", response.toString());
                try {
                    String requestToken = response.getString("request_token");
                    createSession(requestToken);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    /*
    Uma vez que o request token é validado, é solicitada a criação de uma sessão.
     */
    private void createSession(final String requestToken){
        Connection connection = new Connection();

        connection.createSession(requestToken,new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.w("onSuccess", response.toString());
                try {
                    String sessionId = response.getString("session_id");
                    PrefManager.getINSTANCE().saveSessionId(sessionId);
                    getAccountDetails();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(JSONObject error) {
                Log.e("onError",error.toString());
            }
        });
    }

    /*
    Com uma sessão criada e ativa, são solicitados os dados da conta do usuário
     */
    private void getAccountDetails(){
        Connection connection = new Connection();

        connection.getAccountDetails(new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.w("onSuccess", response.toString());
                PrefManager.getINSTANCE().saveUser(response);
                goAhead();
            }

            @Override
            public void onError(JSONObject error) {
                Log.e("onError",error.toString());
            }
        });
    }

    /*
    Segue para a tela principal do aplicativo para início da navegação
     */
    private void goAhead(){
        Intent intent = new Intent(this,MoviesActivity.class);
        startActivity(intent);
    }

}
