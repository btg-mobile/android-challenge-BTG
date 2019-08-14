package com.androidchallengebtg.activities.login;

import android.util.Log;

import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;
import com.androidchallengebtg.helpers.storage.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

class LoginController {

    private Listener listener;

    public interface Listener{
        void onSuccess();
        void onError(String message);
    }

    LoginController(Listener listener) {
        this.listener = listener;
    }

    /*
        Usa o api token para criar um request token
         */
    void createRequestToken(final String username, final String password){

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
                    listener.onError(message);
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
                listener.onSuccess();
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
