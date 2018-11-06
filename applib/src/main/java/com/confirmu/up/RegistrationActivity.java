package com.confirmu.up;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.confirmu.R;
import com.confirmu.up.api.Constants;
import com.confirmu.up.custom_views.RegularTextView;
import com.confirmu.up.model.CountryCode;
import com.confirmu.up.utils.Utils;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 9/28/2017.
 */

public class RegistrationActivity extends Activity {

    RegularTextView tvCountryCode;
    EditText et_mobile_number, etEmail, etName;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String contactStr = "";

    List<CountryCode> countryCodes;
    String cCode = "+91";
    Activity activity;

    private static final String TAG = "RegisterActivityLog";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        activity = this;

        tvCountryCode = (RegularTextView) findViewById(R.id.tv_country_code);
        tvCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //spCountry.performClick();
                Intent intent = new Intent(RegistrationActivity.this, CountryCodeActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        setinitialElements();
    }

    public void setinitialElements() {
        RegularTextView nextBtn = (RegularTextView) findViewById(R.id.btn_go);
        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        etEmail = (EditText) findViewById(R.id.et_email);
        etName = (EditText) findViewById(R.id.et_name);

        progressDialog = new ProgressDialog(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        getCountryCodes();
        initCallback();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String countryISO = null;
        if (telephonyManager != null) {
            countryISO = telephonyManager.getNetworkCountryIso().toUpperCase();
        }

        setCountryCode(countryISO);


        et_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_mobile_number.setError(null);
            }
        });

        et_mobile_number.requestFocus();


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                TBD
//                startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));

                contactStr = et_mobile_number.getText().toString().trim();
                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();

                if (contactStr.isEmpty()) {
                    et_mobile_number.setError("Please enter mobile number");
                    et_mobile_number.requestFocus();
                } else if (contactStr.length() < 10) {
                    et_mobile_number.setError("Please enter valid mobile number");
                    et_mobile_number.requestFocus();
                } else if (name.equals("")) {
                    etName.setError("Please enter name");
                    etName.requestFocus();
                } else if (!name.contains(" ")) {
                    etName.setError("Please enter full name");
                    etName.requestFocus();
                } else if (email.isEmpty()) {
                    etEmail.setError("Please enter Email");
                    etEmail.requestFocus();
                }else if (email.matches(emailPattern)) {
                    if (email.toLowerCase().contains("gmail")) {
                        etEmail.setError("Only professional email ids allowed");
                        etEmail.requestFocus();
                    } else if (email.toLowerCase().contains("yahoo")) {
                        etEmail.setError("Only professional email ids allowed");
                        etEmail.requestFocus();
                    } else if (email.toLowerCase().contains("hotmail")) {
                        etEmail.setError("Only professional email ids allowed");
                        etEmail.requestFocus();
                    }else {
                        progressDialog.show();
                        startPhoneNumberVerification(cCode + contactStr);
                    }
                } else {
                    etEmail.setError("Please enter a valid Email");
                    etEmail.requestFocus();
                    //TODO:
//                    progressDialog.show();
//                    startPhoneNumberVerification(cCode + contactStr);
                }
            }
        });

    }

    String name = "";
    String email = "";

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void initCallback() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.e(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                progressDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.e(TAG, "onVerificationFailed " + e, e);
                mVerificationInProgress = false;
                progressDialog.dismiss();
                Utils.showMessage(activity, "Invalid phone number. Please check and try again.");
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    //Utils.showMessage(activity, "Invalid phone number. Please check and try again.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(activity, "Quota exceeded.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.e(TAG, "onCodeSent:" + verificationId);
                progressDialog.dismiss();
                mVerificationId = verificationId;
                mResendToken = token;
                proceedToVerifyOtp(mVerificationId, cCode + et_mobile_number.getText().toString().trim());
            }
        };
    }

    private void proceedToVerifyOtp(String mVerificationId, String mobileNumber) {
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.verification_id), mVerificationId);
        bundle.putString(getString(R.string.mobile_number), mobileNumber);

        Constants.name = name;
        Constants.email = email;
        Constants.phone = mobileNumber;
        startActivity(new Intent(activity, OTPActivity.class).putExtra(getString(R.string.bundle), bundle));

    }

    private void getCountryCodes() {
        String json = null;
        try {
            InputStream is = getAssets().open("data/phoneCode.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Gson gson = new Gson();
        Type listType = new TypeToken<List<CountryCode>>() {
        }.getType();
        countryCodes = gson.fromJson(json, listType);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1) {
            if (data != null) {
                String message = data.getStringExtra("CODE");
                String name = data.getStringExtra("NAME");
                tvCountryCode.setText(name + " (" + message + ")");
                cCode = message;
            }
        }
    }

    private void setCountryCode(String countryISO) {
        for (CountryCode countryCode : countryCodes) {
            if (countryCode.getCountriesIsoCode().equals(countryISO)) {
                tvCountryCode.setText(countryCode.getCountriesName() + " (" + countryCode.getCountriesIsdCode() + ")");
                //tvCountryCode.setText(countryCode.getCountriesIsdCode());
                cCode = countryCode.getCountriesIsdCode();
            }
        }
    }


    public void hideKeyboard(View view) {
        Utils.hideKeyboard(this);
    }
}
