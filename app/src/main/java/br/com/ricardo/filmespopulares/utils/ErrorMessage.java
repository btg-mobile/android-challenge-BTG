package br.com.ricardo.filmespopulares.utils;

import android.content.Context;
import android.widget.Toast;

public class ErrorMessage {

    public void showError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
