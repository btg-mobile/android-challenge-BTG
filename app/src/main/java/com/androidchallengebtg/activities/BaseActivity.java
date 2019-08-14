package com.androidchallengebtg.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

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
}
