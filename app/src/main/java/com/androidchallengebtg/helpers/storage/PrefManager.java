package com.androidchallengebtg.helpers.storage;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.androidchallengebtg.R;
import com.androidchallengebtg.application.ApplicationBTG;

import org.json.JSONException;
import org.json.JSONObject;

public class PrefManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = ApplicationBTG.getContext().getString(R.string.shared_preferences_name);
    private static PrefManager INSTANCE;

    private PrefManager() {
        pref = ApplicationBTG.getContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public static synchronized PrefManager getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE = new PrefManager();
        }
        return INSTANCE;
    }

    @SuppressLint("ApplySharedPref")
    public void clearSession() {
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    public void saveUser(JSONObject user){
        editor = pref.edit();
        editor.putString("user", user.toString());
        editor.commit();
    }

    public JSONObject getUser(){
        JSONObject user = null;
        String stringUser = pref.getString("user",null);

        try {
            user = new JSONObject(stringUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    @SuppressLint("ApplySharedPref")
    public void saveRequestToken(String requestToken){
        editor = pref.edit();
        editor.putString("request_token", requestToken);
        editor.commit();
    }

    public String getRequestToken(){
        return pref.getString("request_token",null);
    }

    @SuppressLint("ApplySharedPref")
    public void saveSessionId(String sessionId){
        editor = pref.edit();
        editor.putString("session_id", sessionId);
        editor.commit();
    }

    public String getSessionId(){
        return pref.getString("session_id",null);
    }

    @SuppressLint("ApplySharedPref")
    public void savePass(JSONObject pass){
        editor = pref.edit();
        editor.putString("pass", pass.toString());
        editor.commit();
    }

    public JSONObject getPass(){
        JSONObject pass = null;
        String stringPass = pref.getString("pass",null);

        try {
            pass = new JSONObject(stringPass);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return pass;
    }

}
