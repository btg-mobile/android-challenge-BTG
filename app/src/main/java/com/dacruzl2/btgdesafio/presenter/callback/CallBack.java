package com.dacruzl2.btgdesafio.presenter.callback;

public interface CallBack<T> {
    void onSuccess(T response);

    void onError(Throwable t);
}
