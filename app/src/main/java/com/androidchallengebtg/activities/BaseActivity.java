package com.androidchallengebtg.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.androidchallengebtg.R;
import com.androidchallengebtg.activities.login.LoginActivity;
import com.androidchallengebtg.application.ApplicationBTG;
import com.androidchallengebtg.helpers.connection.Connection;
import com.androidchallengebtg.helpers.connection.ConnectionListener;
import com.androidchallengebtg.helpers.storage.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    protected ProgressDialog dialog;

    public void showProgressDialog(final Activity activity, String msg, boolean cancelable) {

        if(dialog==null){
            dialog = new ProgressDialog(activity);
        }

        hideProgressDialog();

        try {
            dialog.setCancelable(cancelable);
            if (!dialog.isShowing()) {
                dialog.setMessage(msg);
                dialog.setOnKeyListener(new Dialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode,
                                         KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            activity.finish();
                            dialog.dismiss();
                        }
                        return true;
                    }
                });
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (dialog != null) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void logout(){
        showProgressDialog(this,getString(R.string.logging_out),false);
        Connection connection = new Connection();
        connection.logout(new ConnectionListener() {
            @Override
            public void onSuccess(JSONObject response) {
                PrefManager.getINSTANCE().clearSession();
                BaseActivity.this.hideProgressDialog();
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(JSONObject error) {
                BaseActivity.this.hideProgressDialog();
                String message = ApplicationBTG.getContext().getString(R.string.unknow_error);
                try {
                    message = error.getString("status_message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showToast(message);
            }
        });
    }
}
