package com.confirmu.up;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.confirmu.R;
import com.confirmu.up.api.Constants;
import com.confirmu.up.api.CustomResponseListener;
import com.confirmu.up.utils.Utils;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import io.fabric.sdk.android.Fabric;

public class IntroActivity extends AppCompatActivity implements CustomResponseListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int MY_SMS_REQUEST_CODE = 100;
    private static final int MY_LOCATION_REQUEST_CODE = 101;
    private static final int CALL_LOG_REQUEST_CODE = 102;
    private static final int READ_CONTACTS_REQUEST_CODE = 104;
    int screen_no;
    ImageView ivIntro;
    TextView ivNext;
    TextView titleText;
    TextView subtitleText;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    JSONArray locationArray = new JSONArray();
    ProgressDialog progressDialog;
    int c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        progressDialog = new ProgressDialog(this);
        screen_no = 0;

        ivIntro = (ImageView)findViewById(R.id.iv_intro);
        titleText = (TextView)findViewById(R.id.title_text);
        subtitleText = (TextView)findViewById(R.id.subtitle_text);
        ivNext = (TextView)findViewById(R.id.iv_next);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (screen_no){
                    case 0:
                        ivIntro.setImageResource(R.drawable.ic_intro_2);
                        ivNext.setText(R.string.enable_sms_service);
                        screen_no = 1;
                        titleText.setText(R.string.intro_2_title);
                        subtitleText.setText(R.string.intro_2_subtitle);
                        break;
                    case 1:
                        /*ivIntro.setImageResource(R.drawable.ic_intro_3);
                        screen_no = 2;*/
                        checkSMSPermission();
                        break;
                    case 2:
                       // startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                        checkLocationPerMission();
                        break;
                }
            }
        });

        titleText.setText(R.string.intro_1_title);
        subtitleText.setText(R.string.intro_1_subtitle);

        createClient();

        getAppKeyHash();
        getPackageHash();
    }


    private void getAppKeyHash() {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;

                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), Base64.NO_WRAP));
                //System.out.println("HASH  " + something);
                Log.e("HASH ", something);
                //showSignedHashKey(something);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {

            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    private void getPackageHash() {
        try {

            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                    "com.confirmu.up",//give your package name here
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("getPackageHash", "Hash  : " + Base64.encodeToString(md.digest(), Base64.NO_WRAP));//Key hash is printing in Log
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getPackageHash", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("getPackageHash", e.getMessage(), e);
        }

    }

    private void checkSMSPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS},
                        MY_SMS_REQUEST_CODE);
            } else {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                goToIntroThree();
            }
        } else {
            // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            goToIntroThree();
        }
    }



    private void checkLocationPerMission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_REQUEST_CODE);
            } else {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                //goToHome();
                checkCallLogPermission();
            }
        } else {
            // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            //goToHome();
            checkCallLogPermission();
        }
    }

    private void checkCallLogPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CALL_LOG)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG},
                        CALL_LOG_REQUEST_CODE);
            } else {
                Constants.callLogsArray = Utils.getCallDetails(this);
                checkContactsPermission();
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            }
        } else {
            Constants.callLogsArray = Utils.getCallDetails(this);
            checkContactsPermission();
            // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
        }
    }

    private void checkContactsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        READ_CONTACTS_REQUEST_CODE);
            } else {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                getContacts();
                goToHome();
            }
        } else {
            // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            getContacts();
            goToHome();
        }
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_SMS_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                goToIntroThree();

            }
            //goToIntroThree();

        }else if (requestCode == MY_LOCATION_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                //goToHome();
                checkCallLogPermission();

            }
           // goToHome();

        }
        else if (requestCode == CALL_LOG_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                //goToHome();
                progressDialog.show();
                Constants.callLogsArray = Utils.getCallDetails(this);
                progressDialog.dismiss();
                checkContactsPermission();

            }
            // goToHome();

        } else if (requestCode == READ_CONTACTS_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                getContacts();
                goToHome();

            }
            // goToHome();

        }
    }

    private void goToIntroThree(){
        ivIntro.setImageResource(R.drawable.ic_intro_3);
        ivNext.setText(R.string.enable_geo_location);
        screen_no = 2;
        titleText.setText(R.string.intro_3_title);
        subtitleText.setText(R.string.intro_3_subtitle);
        sms();
    }

    private void goToHome(){
        sendLocation();
        progressDialog.dismiss();
       // startActivity(new Intent(IntroActivity.this, HomeActivity.class));
        startActivity(new Intent(IntroActivity.this, RegistrationActivity.class));
        finishAffinity();
    }

    public JSONArray getSMS() {
        int m = 0;
        List<String> sms = new ArrayList<String>();
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);

        JSONArray jsonArray = new JSONArray();
        while (cur != null && cur.moveToNext()) {
            String address = cur.getString(cur.getColumnIndex("address"));
            String body = cur.getString(cur.getColumnIndexOrThrow("body"));
            sms.add(body);
            String date = cur.getString(cur.getColumnIndex("date"));
            Long timestamp = Long.parseLong(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            Date finaldate = calendar.getTime();
            String smsDate = finaldate.toString();
            //System.out.println(("SMS" + "\n" + smsDate + "\n" + body + "\n" + address));
            m++;

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", "" + (++m));
                jsonObject.put("body", body);
                jsonObject.put("date", smsDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }

        if (cur != null) {
            cur.close();
        }
        Constants.smsArray = jsonArray;
        return jsonArray;
    }

    private void sms() {

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {
            jsonParams.put("sms", getSMS());

            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       // System.out.println("Params : " + jsonParams.toString());
//        Communicator communicator = new Communicator();
//        communicator.post(this, "1", Constants.SMS, entity, this);
    }

    private void sendLocation() {

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {
            jsonParams.put("location_history", locationArray);
            Constants.locationArray = locationArray;

            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println("Params : " + jsonParams.toString());
//        Communicator communicator = new Communicator();
//        communicator.post(this, "1", Constants.SMS, entity, this);
    }

    @Override
    public void onResponse(String requestCode, String response) {
        //Log.e("onResponse", response);

    }

    @Override
    public void onError(String requestCode, Throwable response) {
        //Log.e("onResponse", response.toString());
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        //Log.e("Location", "onResume");
    }

    /*@Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.e("Location", "onStart");
    }*/

    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void createClient() {
        //Log.e("Location", "createClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //Log.e("Location", "createCliented");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Log.e("Location", "onConnected");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //Log.e("Location", "requested");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
//            Log.e("Location", "Latitude " + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
//            Log.e("Location", "Longitude " + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());

            Long timestamp = Long.parseLong(Calendar.getInstance().getTime().getTime() + "");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            Date finaldate = calendar.getTime();
            String smsDate = finaldate.toString();
            //System.out.println(("SMS" + "\n" + smsDate + "\n" + body + "\n" + address));

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", "" + (c + 1));
                jsonObject.put("lat", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
                jsonObject.put("lng", LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
                jsonObject.put("timestamp", smsDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            locationArray.put(jsonObject);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.e("Location", "onLocationChanged");
        //Toast.makeText(context, "Location received: " + location.toString(), Toast.LENGTH_SHORT).show();

        Long timestamp = Long.parseLong(Calendar.getInstance().getTime().getTime() + "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        Date finaldate = calendar.getTime();
        String smsDate = finaldate.toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "" + (c + 1));
            jsonObject.put("lat", location.getLatitude());
            jsonObject.put("lng", location.getLongitude());
            jsonObject.put("timestamp", smsDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        locationArray.put(jsonObject);
    }

    private void getContacts(){
        /*new Handler().post(new Runnable() {
            @Override
            public void run() {
                Constants.contactsArray = Utils.getContactList(IntroActivity.this);
            }
        });*/
        progressDialog.show();
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Constants.contactsArray = Utils.getContactList(IntroActivity.this);
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
           // progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


}
