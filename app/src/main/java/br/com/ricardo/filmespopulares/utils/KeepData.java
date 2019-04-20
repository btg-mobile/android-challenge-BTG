package br.com.ricardo.filmespopulares.utils;

import android.content.SharedPreferences;

public class KeepData {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public KeepData(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public void saveFlagFavoriteMovie(String idMovie, boolean value){
        editor.putBoolean(idMovie, value);
        editor.apply();
    }

    public boolean recoverFlagFavorite(String idMovie){

        return prefs.getBoolean(idMovie, false);
    }

}
