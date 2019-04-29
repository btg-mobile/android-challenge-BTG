package com.dacruzl2.btgdesafio;

import com.facebook.stetho.Stetho;

public class Application extends android.app.Application {

    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
