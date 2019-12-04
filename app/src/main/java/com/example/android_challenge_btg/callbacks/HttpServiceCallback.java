package com.example.android_challenge_btg.callbacks;

public interface HttpServiceCallback {
    void onSuccess(String response);
    void onError(String message);
}
