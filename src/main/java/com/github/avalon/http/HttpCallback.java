package com.github.avalon.http;

public interface HttpCallback {

    void onResponse(String response);

    void onError(Throwable throwable);

}
