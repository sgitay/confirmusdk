package com.confirmu.up.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.KeyStore;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * Created by arthonsystechnologiesllp on 03/03/17.
 */

public class Communicator {

    // For library
    public static void configureCommunicator(final String Access_Token,final String Partner){
        Constants.Access_Token = Access_Token;
        Constants.Partner = Partner;
    }

    public void post(final String reqCode, final Context context, String url, RequestParams params, final CustomResponseListener responseListener) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.setResponseTimeout(30000);
        if(Constants.Access_Token!=null && Constants.Partner!=null)
        {
            client.addHeader("Access-Token",Constants.Access_Token);
            client.addHeader("Partner",Constants.Partner);
        }
        client.addHeader("Language",getSelectedLanguage(context));


        /// We initialize a default Keystore
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // We load the KeyStore
            trustStore.load(null, null);

            com.loopj.android.http.MySSLSocketFactory sslSocketFactory = new com.loopj.android.http.MySSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(com.loopj.android.http.MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sslSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                responseListener.onResponse(reqCode, response.trim());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseListener.onError(reqCode, error);
            }

        });

    }

    public void post(Context context, final String reqCode, String url, StringEntity entity, final CustomResponseListener responseHandler){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(30000);
        client.setResponseTimeout(30000);
        if(Constants.Access_Token!=null && Constants.Partner!=null) {
            client.addHeader("Access-Token", Constants.Access_Token);
            client.addHeader("Partner", Constants.Partner);
        }
        client.addHeader("Language",getSelectedLanguage(context));

        /// We initialize a default Keystore
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // We load the KeyStore
            trustStore.load(null, null);

            com.loopj.android.http.MySSLSocketFactory sslSocketFactory = new com.loopj.android.http.MySSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(com.loopj.android.http.MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sslSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.post(context, url, entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                responseHandler.onResponse(reqCode, response.trim());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseHandler.onError(reqCode, error);
            }
        });
    }

    public void postMultipart(final String reqCode, final Context context, String url, RequestParams params, final CustomResponseListener responseListener) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "multipart/form-data");
        client.setTimeout(10000);
        client.setResponseTimeout(10000);
        if(Constants.Access_Token!=null && Constants.Partner!=null) {
            client.addHeader("Access-Token", Constants.Access_Token);
            client.addHeader("Partner", Constants.Partner);
        }
        client.addHeader("Language",getSelectedLanguage(context));

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
// We load the KeyStore
            trustStore.load(null, null);
            com.loopj.android.http.MySSLSocketFactory sslSocketFactory = new com.loopj.android.http.MySSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(com.loopj.android.http.MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(new com.loopj.android.http.MySSLSocketFactory(trustStore));
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                responseListener.onResponse(reqCode, response.trim());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseListener.onError(reqCode, error);
            }

        });

    }

    public void get(final String reqCode, final Context context, String url, final CustomResponseListener responseListener) {
        AsyncHttpClient client = new AsyncHttpClient();
        if(Constants.Access_Token!=null && Constants.Partner!=null) {
            client.addHeader("Access-Token", Constants.Access_Token);
            client.addHeader("Partner", Constants.Partner);
        }
        client.addHeader("Language",getSelectedLanguage(context));

        client.setMaxRetriesAndTimeout(1, 10000);
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
// We load the KeyStore
            trustStore.load(null, null);
            com.loopj.android.http.MySSLSocketFactory sslSocketFactory = new com.loopj.android.http.MySSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(com.loopj.android.http.MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sslSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.get(context, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                responseListener.onResponse(reqCode, response.trim());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseListener.onError(reqCode, error);
            }

        });

    }

    public void get(final String reqCode, final Context context, String url, RequestParams params, final CustomResponseListener responseListener) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setMaxRetriesAndTimeout(2, 10000);
        if(Constants.Access_Token!=null && Constants.Partner!=null) {
            client.addHeader("Access-Token", Constants.Access_Token);
            client.addHeader("Partner", Constants.Partner);
        }
        client.addHeader("Language",getSelectedLanguage(context));

        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
// We load the KeyStore
            trustStore.load(null, null);
            com.loopj.android.http.MySSLSocketFactory sslSocketFactory = new com.loopj.android.http.MySSLSocketFactory(trustStore);
            sslSocketFactory.setHostnameVerifier(com.loopj.android.http.MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sslSocketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.get(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                responseListener.onResponse(reqCode, response.trim());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseListener.onError(reqCode, error);
            }

        });

    }

    public String getSelectedLanguage(Context myContext) {
        SharedPreferences prefs = myContext.getSharedPreferences(Constants.Locale_Preference, Activity.MODE_PRIVATE);
        String restoredText = prefs.getString(Constants.SELECTED_LANG, "en");
        Log.d("language", restoredText);
        if (restoredText != null) {
            return restoredText;
        }
        else {
            return "en";
        }
    }

}