package com.confirmu.up;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.confirmu.R;
import com.confirmu.up.custom_views.RegularTextView;
import com.confirmu.up.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 9/28/2017.
 */

public class OTPActivity extends Activity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    TextView tv_timer;
    RegularTextView btResendOtp;
    String otp = "";
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinFifthDigitEditText;
    private EditText mPinSixthDigitEditText;
    private EditText mPinHiddenEditText;

    private FirebaseAuth mAuth;
    String verificationId = "", mobileNumber = "";
    ProgressDialog progressDialog;
    private static final String TAG = "RegisterActivityLog";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * Initialize EditText fields.
     */
    private void init() {


        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Bundle bundle = getIntent().getBundleExtra(getString(R.string.bundle));
        verificationId = bundle.getString(getString(R.string.verification_id));
        mobileNumber = bundle.getString(getString(R.string.mobile_number));

        mPinFirstDigitEditText = (EditText) findViewById(R.id.pin_first_edittext);
        mPinSecondDigitEditText = (EditText) findViewById(R.id.pin_second_edittext);
        mPinThirdDigitEditText = (EditText) findViewById(R.id.pin_third_edittext);
        mPinForthDigitEditText = (EditText) findViewById(R.id.pin_forth_edittext);
        mPinFifthDigitEditText = (EditText) findViewById(R.id.pin_fifth_edittext);
        mPinSixthDigitEditText = (EditText) findViewById(R.id.pin_sixth_edittext);
        mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);


        setInitialElements();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPinFirstDigitEditText.requestFocus();
            }
        }, 200);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainLayout(this, null));

        init();
        try {
            setPINListeners();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setInitialElements() {


        tv_timer = (TextView) findViewById(R.id.tv_timer);
        TextView tvEnter = (TextView) findViewById(R.id.tv_enter);

        tvEnter.setText(String.format(getResources().getString(R.string.please_type), mobileNumber));


        final RegularTextView nextBtn = (RegularTextView) findViewById(R.id.btn_go);

        initCallback();
        btResendOtp = (RegularTextView) findViewById(R.id.btn_resend);
       // btResendOtp.setEnabled(false);
        btResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
                progressDialog.show();
                startPhoneNumberVerification(mobileNumber);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(OTPActivity.this, str.toString(), Toast.LENGTH_SHORT).show();

            /*    otpStr = et_otp.getText().toString();


                if (otpStr.isEmpty()) {
                    et_otp.setError("Please enter otp");
                    et_otp.requestFocus();
                } else if (otpStr.length() < 6) {
                    et_otp.setError("Please enter valid otp number");
                    et_otp.requestFocus();
                } else {
*/
                if (otp.length() > 5) {
                    //Log.e("OTP", otp);
                    //TODO:
                    verifyPhoneNumberWithCode(null);
                } else {
                    Toast.makeText(OTPActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
                    //Log.e("OTP", otp);
                }
            }
            // }
        });


    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        if (id == R.id.pin_first_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }

        } else if (id == R.id.pin_second_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }

        } else if (id == R.id.pin_third_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }

        } else if (id == R.id.pin_forth_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }

        } else if (id == R.id.pin_fifth_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }

        } else if (id == R.id.pin_sixth_edittext) {
            if (hasFocus) {
                setFocus(mPinHiddenEditText);
                showSoftKeyboard(mPinHiddenEditText);
            }

        } else {
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            if (id == R.id.pin_hidden_edittext) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    //if (mPinHiddenEditText.getText().length() == 6)
                    mPinSixthDigitEditText.setText("");
                    //else if (mPinHiddenEditText.getText().length() == 5)
                    mPinFifthDigitEditText.setText("");
                    //else if (mPinHiddenEditText.getText().length() == 4)
                    mPinForthDigitEditText.setText("");
                    //else if (mPinHiddenEditText.getText().length() == 3)
                    mPinThirdDigitEditText.setText("");
                    // else if (mPinHiddenEditText.getText().length() == 2)
                    mPinSecondDigitEditText.setText("");
                    //else if (mPinHiddenEditText.getText().length() == 1)
                    mPinFirstDigitEditText.setText("");

                    if (mPinHiddenEditText.length() > 0)
                        mPinHiddenEditText.setText(/*mPinHiddenEditText.getText().subSequence(0, mPinHiddenEditText.length() - 1)*/"");

                    return true;
                }


            } else {
                return false;
            }
        }


        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setDefaultPinBackground(mPinFirstDigitEditText);
        setDefaultPinBackground(mPinSecondDigitEditText);
        setDefaultPinBackground(mPinThirdDigitEditText);
        setDefaultPinBackground(mPinForthDigitEditText);
        setDefaultPinBackground(mPinFifthDigitEditText);
        setDefaultPinBackground(mPinSixthDigitEditText);
        otp = String.valueOf(s);
        //Log.e("OTP", otp);

        if (s.length() == 0) {
            setFocusedPinBackground(mPinFirstDigitEditText);
            mPinFirstDigitEditText.setText("");
        } else if (s.length() == 1) {
            setFocusedPinBackground(mPinSecondDigitEditText);
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 2) {
            setFocusedPinBackground(mPinThirdDigitEditText);
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 3) {
            setFocusedPinBackground(mPinForthDigitEditText);
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 4) {
            setFocusedPinBackground(mPinFifthDigitEditText);
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinFifthDigitEditText.setText("");
            mPinSixthDigitEditText.setText("");
        } else if (s.length() == 5) {
            setDefaultPinBackground(mPinSixthDigitEditText);
            mPinFifthDigitEditText.setText(s.charAt(4) + "");
            mPinSixthDigitEditText.setText("");
            //hideSoftKeyboard(mPinFifthDigitEditText);
        } else if (s.length() == 6) {
            setDefaultPinBackground(mPinSixthDigitEditText);
            mPinSixthDigitEditText.setText(s.charAt(5) + "");
            hideSoftKeyboard(mPinSixthDigitEditText);
        }
    }

    /**
     * Sets default PIN background.
     *
     * @param editText edit text to change
     */
    private void setDefaultPinBackground(EditText editText) {
        // setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_default_holo_light));
    }

    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
    private void setFocusedPinBackground(EditText editText) {
        //   setViewBackground(editText, getResources().getDrawable(R.drawable.textfield_focused_holo_light));
    }

    /**
     * Sets listeners for EditText fields.
     */
    private void setPINListeners() throws InterruptedException {
        mPinHiddenEditText.addTextChangedListener(this);

        mPinFirstDigitEditText.setOnFocusChangeListener(this);
        mPinSecondDigitEditText.setOnFocusChangeListener(this);
        mPinThirdDigitEditText.setOnFocusChangeListener(this);
        mPinForthDigitEditText.setOnFocusChangeListener(this);
        mPinFifthDigitEditText.setOnFocusChangeListener(this);
        mPinSixthDigitEditText.setOnFocusChangeListener(this);

        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinFifthDigitEditText.setOnKeyListener(this);
        mPinSixthDigitEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);
        //mPinHiddenEditText.setOnKeyListener(this);

//        Utils.showKeyboard(this);


    }

    /**
     * Sets background of the view.
     * This method varies in implementation depending on Android SDK version.
     *
     * @param view       View to which set background
     * @param background Background to set to view
     */
    @SuppressWarnings("deprecation")
    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    private void startTimer() {

        btResendOtp.setEnabled(false);
        btResendOtp.setTextColor(getResources().getColor(R.color.light_grey));
        new CountDownTimer(2 * 60000, 1000) {

            @Override
            public void onTick(long l) {
//                tv_timer.setText(new SimpleDateFormat(":ss").format(new Date(l)));
                tv_timer.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l))));

            }

            @Override
            public void onFinish() {
                tv_timer.setText("" + String.format("%02d : %02d", 00, 00));
                btResendOtp.setEnabled(true);
                btResendOtp.setTextColor(getResources().getColor(R.color.black));
            }
        }.start();
    }

    /**
     * Custom LinearLayout with overridden onMeasure() method
     * for handling software keyboard show and hide events.
     */
    public class MainLayout extends LinearLayout {

        public MainLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_otp, this);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
            final int actualHeight = getHeight();

            Log.d("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

            if (actualHeight >= proposedHeight) {
                // Keyboard is shown
                if (mPinHiddenEditText.length() == 0)
                    setFocusedPinBackground(mPinFirstDigitEditText);
                else
                    setDefaultPinBackground(mPinFirstDigitEditText);
            }

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    public void verifyPhoneNumberWithCode(View view) {
        if (Utils.isNetworkAvailable(this)){
            if (!otp.trim().equals("")){
                // [START verify_with_code]
                progressDialog.show();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                // [END verify_with_code]
                signInWithPhoneAuthCredential(credential);
            }else {
                Utils.showMessage(this, "Please enter OTP received via SMS to your entered mobile number");
            }
        }else {
            Utils.showMessage(this, "Please check your internet connection and try again");
        }
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                           // Utils.showMessage(OTPActivity.this, "Success");
                            startActivity(new Intent(OTPActivity.this, HomeActivity.class));
                            finishAffinity();
                            // [START_EXCLUDE]
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                Utils.showMessage(OTPActivity.this, "Invalid OTP.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]



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
                Log.e(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;
                progressDialog.dismiss();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Utils.showMessage(OTPActivity.this, "Invalid phone number. Please check and try again.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(OTPActivity.this, "Quota exceeded.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.e(TAG, "onCodeSent:" + verificationId);
                progressDialog.dismiss();
                mVerificationId = verificationId;
                mResendToken = token;
                Utils.showMessage(OTPActivity.this, "Otp sent to " + mobileNumber);
            }
        };
    }


}


