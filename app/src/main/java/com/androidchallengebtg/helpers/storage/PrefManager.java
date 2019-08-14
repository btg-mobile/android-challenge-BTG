package com.androidchallengebtg.helpers.storage;

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

    public void clearSession() {
        editor = pref.edit();
        editor.clear();
        editor.commit();
    }

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
}
