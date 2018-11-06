package com.confirmu.up.api;

/**
 * Created by admin on 21-Aug-17.
 */

public interface CustomResponseListener {
    void onResponse(String requestCode, String response);
    void onError(String requestCode, Throwable response);
}
