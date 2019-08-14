package com.androidchallengebtg.application;

import android.app.Application;
import android.content.Context;

import com.androidchallengebtg.helpers.connection.ConnectionQueue;

public class ApplicationBTG extends Application {

    private static ApplicationBTG mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectionQueue.getINSTANCE().startQueue(this);
        mInstance = this;
    }

    public static synchronized Context getContext() {
        return mInstance.getApplicationContext();
    }

}
