package com.androidchallengebtg.helpers.connection;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ConnectionQueue {

    private static ConnectionQueue INSTANCE;
    private RequestQueue rq;

    public static synchronized ConnectionQueue getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE = new ConnectionQueue();
        }
        return INSTANCE;
    }

    public void startQueue(Context c){
        if(rq==null){
            this.rq = Volley.newRequestQueue(c);
        }
    }

    public void addQueue(Request request){
        this.rq.add(request);
    }

    public void cancelRequest(String tag){
        this.rq.cancelAll(tag);
    }

    public void cancelAllRequests(Context c){
        this.rq.cancelAll(c);
    }
}
