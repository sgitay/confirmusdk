package com.confirmu.up;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.confirmu.R;
import com.confirmu.up.adapters.ChatAdapter;
import com.confirmu.up.adapters.SelectedSkillsAdapter;
import com.confirmu.up.adapters.SkillsAdapter;
import com.confirmu.up.api.Communicator;
import com.confirmu.up.api.Constants;
import com.confirmu.up.api.CustomResponseListener;
import com.confirmu.up.model.Message;
import com.confirmu.up.utils.AboutActivity;
import com.confirmu.up.utils.SpeedyLinearLayoutManager;
import com.confirmu.up.utils.Utils;
import com.confirmu.up.utils.ViewAnimationUtils;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.loopj.android.http.RequestParams;
import com.rw.keyboardlistener.KeyboardUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import cz.msebera.android.httpclient.entity.StringEntity;
import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;

public class ChatActivity extends AppCompatActivity implements CustomResponseListener {


    private static final int MY_SMS_REQUEST_CODE = 100;
    public EditText etAnswer;
    public String FirstName = "";
    public String LastName = "";
    public String DateOfBirth = "";
    public String EmailAddress = "";
    public String Gender = "";
    public String MaritalStatus = "";
    public String AGE = "";
    public String KIDS = "";
    public String LOAN_NAME = "";
    public boolean aadharInfoUpdated = false;
    public boolean genderSelected = false;
    public boolean maritalStatus = false;
    public boolean socialLogin = false;
    public boolean skills = false;
    public boolean living = false;
    public boolean Like = false;
    public boolean Submitted = false;
    public boolean FB_LOGIN = false, LINKED_IN = false;
    boolean menuOpen = false;
    View transparentLayer;
    ProgressDialog progressDialog;
    String loanType;
    List<Message> chatList;
    int question_no = 0;
    String question = "";
    boolean personalChat = true;
    String personalQuestions[], reviewQuestions[];
    JSONObject questionList;
    int questionsSize = 0;
    RecyclerView rvChat;
    RecyclerView rvSelectedSkills;
    int i = 0;
    List<String> skillsList, selectedSkills, answersList;
    CallbackManager callbackManager;
    String CustomerNumber = "";
    boolean verified = false;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int j = 0;
    int image, requestedAmount, emi;

    int socialAccounts = 0;
    boolean PROCEED = false;

    JSONArray industryJsonArray = new JSONArray();
    JSONObject fbObject = new JSONObject();
    JSONObject linkedInData = new JSONObject();

    //New Task Start Here...
    JSONArray questionJSONArray;



    // Build the list of member permissions our LinkedIn session requires
    private static Scope buildScope() {
        //Check Scopes in Application Settings before passing here else you won't able to read that data
        return Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS/*, Scope.R_FULLPROFILE*//*, Scope.R_CONTACTINFO, Scope.RW_COMPANY_ADMIN, Scope.R_FULLPROFILE*/);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_chat);
        init();

    }

    private void init() {

        EmailAddress = Constants.email;
        CustomerNumber = Constants.phone;
        StringTokenizer stringTokenizer = new StringTokenizer(Constants.name, " ");
        FirstName = stringTokenizer.nextToken();
        LastName = stringTokenizer.nextToken();


        callbackManager = CallbackManager.Factory.create();

        transparentLayer = findViewById(R.id.transparent_layer);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        // fadeOut.setStartOffset(1000);
        fadeOut.setDuration(10); //time in milliseconds
        fadeOut.setFillAfter(true);
        transparentLayer.startAnimation(fadeOut);


        loanType = getIntent().getStringExtra(Constants.Keys.LOAN_TYPE);
        ((TextView) findViewById(R.id.tv_title)).setText(getIntent().getStringExtra(Constants.Keys.LOAN_NAME));
        LOAN_NAME = getIntent().getStringExtra(Constants.Keys.LOAN_NAME);
        image = getIntent().getIntExtra("image", 0);
        requestedAmount = getIntent().getIntExtra("loan_amount", 0);
        emi = getIntent().getIntExtra("emi", 0);

        progressDialog = new ProgressDialog(this);
        if (Utils.isNetworkAvailable(this)) {
            getQuestions();
        } else {
            Toast.makeText(this, "Please check your internet connection and try again!", Toast.LENGTH_LONG).show();
        }

        etAnswer = (EditText) findViewById(R.id.et_message);

        rvChat = (RecyclerView) findViewById(R.id.rv_chat);
        rvChat.setNestedScrollingEnabled(true);
        rvChat.setHasFixedSize(true);
        rvChat.setLayoutManager(new SpeedyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatList = new ArrayList<>();
        rvChat.setAdapter(new ChatAdapter(this, chatList, rvChat));

        personalQuestions = getResources().getStringArray(R.array.questions);
        reviewQuestions = getResources().getStringArray(R.array.review_questions);

        answersList = new ArrayList<>();

        checkSMSPermission();

        KeyboardUtils.addKeyboardToggleListener(this, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                //Log.d("keyboard", "keyboard visible: "+isVisible);
                if (isVisible) {
                    rvChat.smoothScrollToPosition(chatList.size() - 1);
                }
            }
        });


    }

    public void rotate(View view) {
        ImageView image = (ImageView) findViewById(R.id.iv_menu);
        CardView cvMenu = (CardView) findViewById(R.id.cv_menu);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(700);
        fadeIn.setFillAfter(true);//time in milliseconds

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        // fadeOut.setStartOffset(1000);
        fadeOut.setDuration(700); //time in milliseconds
        fadeOut.setFillAfter(true); //time in milliseconds

        /*AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        transparentLayer.setAnimation(animation);*/


        RotateAnimation rotate;
        if (menuOpen) {
            rotate = new RotateAnimation(90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ViewAnimationUtils.collapse(cvMenu);
            transparentLayer.startAnimation(fadeOut);
            transparentLayer.setClickable(false);
            transparentLayer.setFocusable(false);
            menuOpen = false;
        } else {
            rotate = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ViewAnimationUtils.expand(cvMenu);
            transparentLayer.startAnimation(fadeIn);
            transparentLayer.setClickable(true);
            transparentLayer.setFocusable(true);
            menuOpen = true;
        }

        rotate.setDuration(500);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new FastOutSlowInInterpolator());
        image.startAnimation(rotate);
    }

    private void getQuestions() {

        JSONObject jsonParams = new JSONObject();
        /*StringEntity entity = null;
        try {
            jsonParams.put("first_name", etName.getText().toString().trim());
            jsonParams.put("contact", etMobile.getText().toString().trim());
            jsonParams.put("password", etPassword.getText().toString().trim());
            jsonParams.put("role_id", roleId);
            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Params : " + jsonParams.toString());*/
        Communicator communicator = new Communicator();
        RequestParams rparams = new RequestParams();
        rparams.add("language", getSelectedLanguage(this));
        try {
            Log.d("Selected Language", getSelectedLanguage(this));
            communicator.post(this, "1", Constants.GET_QUESTIONS, new StringEntity(""), this);
//            communicator.post(this, "1", "http://confirmu-test.us-east-2.elasticbeanstalk.com/api/getquestions/", new StringEntity(""), this);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sms() {

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {
            jsonParams.put("credit_type", loanType);
            JSONObject answers = new JSONObject();
            for (int i = 0; i < answersList.size(); i++) {
                answers.put("question_" + (i + 1), answersList.get(i));
            }
            jsonParams.put("customer", answers);
            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Params : " + jsonParams.toString());
        Communicator communicator = new Communicator();
        communicator.post(this, "2", Constants.SMS, entity, this);
        progressDialog.show();
    }

    public void postPersonalData() {

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {

            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String todayString = formatter.format(todayDate);

            JSONObject customerJsonObject = new JSONObject();
            customerJsonObject.put("RegistrationDate", todayString);
            customerJsonObject.put("CustomerNumber", CustomerNumber);
            customerJsonObject.put("FirstName", FirstName);
            customerJsonObject.put("LastName", LastName);
            customerJsonObject.put("PersonalNumber", CustomerNumber);
            customerJsonObject.put("PhoneNumber", CustomerNumber);
            customerJsonObject.put("CellNumber", CustomerNumber);
            customerJsonObject.put("DateOfBirth", "1985-09-05");
            customerJsonObject.put("EmailAddress", EmailAddress);
            customerJsonObject.put("ProfileImageSize", 0);
            customerJsonObject.put("ProfileImage", "null");
            customerJsonObject.put("Gender", Gender);
            customerJsonObject.put("MaritalStatus", MaritalStatus);
            customerJsonObject.put("Education", "null");
            JSONObject answers = new JSONObject();
            StringBuilder answerString = new StringBuilder();
            for (int i = 0; i < answersList.size(); i++) {
                answers.put("question_" + (i + 1), answersList.get(i));
                answerString.append(" ").append(answersList.get(i));
            }
            //customerJsonObject.put("AboutMe", answers);
            customerJsonObject.put("AboutMe", answerString);

            JSONObject skillsJsonObject = new JSONObject();
            for (int i = 0; i < selectedSkills.size(); i++) {
                /*skillsJsonObject.put("Key", (i + 1));
                skillsJsonObject.put("Value", selectedSkills.get(i));*/

                skillsJsonObject.put("Key" + (i + 1), (i + 1));
                skillsJsonObject.put("Value" + (i + 1), selectedSkills.get(i));
            }
            customerJsonObject.put("Skills", skillsJsonObject);
            customerJsonObject.put("facebook", fbObject);
            customerJsonObject.put("linkedin", linkedInData);
            customerJsonObject.put("sms", Constants.smsArray);
            customerJsonObject.put("location", Constants.locationArray);
            customerJsonObject.put("call_logs", Constants.callLogsArray);
            customerJsonObject.put("contacts", Constants.contactsArray);
            customerJsonObject.put("industry_data", industryJsonArray);

            JSONObject loan_request = new JSONObject();

            loan_request.put("loan_type", loanType);
            loan_request.put("requested_amount", requestedAmount);
            loan_request.put("monthly_repayment_amount", emi);
            loan_request.put("loan_duration", getIntent().getIntExtra("duration", 0) + "");
            customerJsonObject.put("loan_request", loan_request);

            jsonParams.put("customer", customerJsonObject);
            entity = new StringEntity(jsonParams.toString());
            //Log.e("Params", jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Communicator communicator = new Communicator();
        communicator.post(this, "2", Constants.GET_SCORES, entity, this);
        progressDialog.show();
    }

    @Override
    public void onResponse(String requestCode, String response) {
       // Log.e("onResponse", response);
        progressDialog.dismiss();
        //Gson gson = new Gson();
        switch (requestCode) {
            case "1":
                try {
            /*QuestionsResponse questionsResponse = gson.fromJson(response, QuestionsResponse.class);
            if (questionsResponse.getIsSuccess()){
                Questions questions = questionsResponse.getQuestions();
                if (loanType.equals("car")){
                    Car car = questions.getCar();
                    setupChat(car);
                }
            }else {
                Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }*/

                    JSONObject questionsResponseJsonObject = new JSONObject(response);
                    if (questionsResponseJsonObject.getBoolean("IsSuccess")) {
                        JSONObject questionsJsonObject = questionsResponseJsonObject.getJSONObject(Constants.Keys.QUESTIONS);
                        /*questionList = questionsJsonObject.getJSONObject(loanType);
                        if (loanType.equals(Constants.Keys.CAR)) {
                            questionsSize = 13;
                        } else if (loanType.equals(Constants.Keys.WHEELERS)) {
                            questionsSize = 13;
                        } else if (loanType.equals(Constants.Keys.HOME_LOAN)) {
                            questionsSize = 12;
                        } else {
                            questionsSize = 23;
                        }*/

                        questionJSONArray = questionsJsonObject.getJSONArray(loanType);
                        questionsSize = questionJSONArray.length();
                        setupPersonalInfoChat();
                    }

                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                break;
            case "2":
                try {
                    JSONObject questionsResponseJsonObject = new JSONObject(response);
                    if (questionsResponseJsonObject.getBoolean("IsSuccess")) {
                        String message = questionsResponseJsonObject.getString("Message");
                        int number = questionsResponseJsonObject.getInt("Number");
                        //Toast.makeText(this, "" + record, Toast.LENGTH_SHORT).show();
                        openResultDialog(message, number);
                    }
                } catch (Exception e) {
                    //Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    openResultDialog("", 0);
                }
                break;
        }
    }

    @Override
    public void onError(String requestCode, Throwable response) {
       // Log.e("onResponse", response.toString());
        progressDialog.dismiss();
        //Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
        if (requestCode.equals("2")) {
            openResultDialog("", 0);
        }
    }

    private void setupChat() {
        JSONObject questionJsonObject = null;
        try {
            //chatList.add(new Message(questionList.getString(Constants.Keys.QUESTION_ + (++question_no)), 1));
            questionJsonObject = questionJSONArray.getJSONObject(question_no++);
            chatList.add(new Message(questionJsonObject.getString("question"), 1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });

        try {
            if (questionJsonObject.getString("type").equalsIgnoreCase("mcq")){
                try {
                    //chatList.add(new Message(questionJsonObject.getJSONArray("options"), 15));
                    updateListWithDelay(new Message(questionJsonObject.getJSONArray("options"), 15));
                    etAnswer.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                etAnswer.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addAnswerToChat(View view) {
        if (personalChat) {
            if (!etAnswer.getText().toString().trim().equals("")) {
                if (i < personalQuestions.length - 1) {
                    if (i == 6) {
                        CustomerNumber = etAnswer.getText().toString().trim();
                    }
                    if (i == 5) {
                            if (etAnswer.getText().toString().trim().matches(emailPattern)) {
                                String email = etAnswer.getText().toString().trim();
                                if (email.toLowerCase().contains("gmail")) {
                                    Utils.showMessage(ChatActivity.this, "Only professional email ids allowed");
                                    return;
                                } else if (email.toLowerCase().contains("yahoo")) {
                                    Utils.showMessage(ChatActivity.this, "Only professional email ids allowed");
                                    return;
                                } else if (email.toLowerCase().contains("hotmail")) {
                                    Utils.showMessage(ChatActivity.this, "Only professional email ids allowed");
                                    return;
                                } else {
                                    EmailAddress = etAnswer.getText().toString().trim();
                                }
                            } else {
                            chatList.add(new Message(etAnswer.getText().toString().trim(), 2));
                            updateList();
                            //chatList.add(new Message(personalQuestions[4], 1));

                            /*rvChat.post(new Runnable() {
                                @Override
                                public void run() {
                                    rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
                                    rvChat.smoothScrollToPosition(chatList.size() - 1);
                                }
                            });*/

                            updateListWithDelay(new Message(personalQuestions[4], 1));
                            etAnswer.setText("");

                            Utils.showMessage(ChatActivity.this, "Please enter a valid Email");
                            return;
                        }
                    }
                    if (i == 3) {
                        if (etAnswer.getText().toString().trim().contains(" ")) {
                            StringTokenizer stringTokenizer = new StringTokenizer(etAnswer.getText().toString().trim(), " ");
                            FirstName = stringTokenizer.nextToken();
                            LastName = stringTokenizer.nextToken();
                        } else {
                            chatList.add(new Message(etAnswer.getText().toString().trim(), 2));
                            updateList();
                            //chatList.add(new Message(personalQuestions[2], 1));
                            /*rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
                            rvChat.post(new Runnable() {
                                @Override
                                public void run() {
                                    rvChat.smoothScrollToPosition(chatList.size() - 1);
                                }
                            });*/
                            updateListWithDelay(new Message(personalQuestions[2], 1));
                            etAnswer.setText("");
                            return;
                        }
                    }
                    if (i == 2) {
                        if (etAnswer.getText().toString().trim().contains(" ")) {
                            StringTokenizer stringTokenizer = new StringTokenizer(etAnswer.getText().toString().trim(), " ");
                            FirstName = stringTokenizer.nextToken();
                            LastName = stringTokenizer.nextToken();
                            i++;
                        }
                    }
                    if (i == 14) {
                        AGE = etAnswer.getText().toString().trim();
                    }
                    if (i == 15) {
                        KIDS = etAnswer.getText().toString().trim();
                    }
                    chatList.add(new Message(etAnswer.getText().toString().trim(), 2));
                    updateList();
                    if (i == 15) {
                        //updateList();
                        //chatList.add(new Message(personalQuestions[i++], 1, "clickable"));
                        updateListWithDelay(new Message(personalQuestions[i++], 1, "clickable"));
                        etAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
                        Utils.hideKeyboard(this);
                    } else if (i == 14 || i == 13 || i == 5) {
                        //updateList();
                        //chatList.add(new Message(personalQuestions[i++], 1));
                        updateListWithDelay(new Message(personalQuestions[i++], 1));
                        etAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else {
                        //updateList();
                        //chatList.add(new Message(personalQuestions[i++], 1));
                        updateListWithDelay(new Message(personalQuestions[i++], 1));
                        etAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                    if (i == 4 || i == 10 || i == 12) {
                        //updateList();
                        // chatList.add(new Message(personalQuestions[i++], 1, "2"));
                        updateListWithDelay(new Message(personalQuestions[i++], 1, "2"));
                    }
                    if (i == 7) {
                        //updateList();
                        // chatList.add(new Message("", 4));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateListWithDelay(new Message("", 4));
                            }
                        }, 1500);
                        Utils.hideKeyboard(this);
                    }
                    if (i == 8) {
                        //updateList();
                        // chatList.add(new Message("", 7, ""));
                        updateListWithDelay(new Message("", 7, ""));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateListWithDelay(new Message(personalQuestions[i++], 1, "2"));
                            }
                        }, 1000);
                        //chatList.add(new Message(personalQuestions[i++], 1, "2"));
                        Utils.hideKeyboard(this);
                    }
                    if (i == 11) {
                        //updateList();
                        //chatList.add(new Message("", 5));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateListWithDelay(new Message("", 5));
                            }
                        }, 1000);
                        Utils.hideKeyboard(this);
                    }
                    if (i == 13) {
                        //updateList();
                        // chatList.add(new Message("", 6));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateListWithDelay(new Message("", 6));
                            }
                        }, 1000);
                        Utils.hideKeyboard(this);
                    }
                    if (i == 17) {
                        //updateList();
                        //chatList.add(new Message("", 8));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateListWithDelay(new Message("", 8));
                            }
                        }, 1000);
                        Utils.hideKeyboard(this);
                    }
                    if (i == 18) {
                        //updateList();
                        //chatList.add(new Message("", 10));
                        updateListWithDelay(new Message("", 10));
                        Utils.hideKeyboard(this);
                    }
                    /*rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
                    rvChat.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvChat.smoothScrollToPosition(chatList.size() - 1);
                        }
                    }, 5000);*/
                    etAnswer.setText("");

                    if (question_no > questionsSize) {
                        setupChat();
                        Utils.hideKeyboard(this);
                    }
                } else {
                    etAnswer.setText("");
                    setupChat();
                    personalChat = false;
                }
                //rvChat.getAdapter().notifyDataSetChanged();

            }
        } else {
            if (!etAnswer.getText().toString().trim().equals("")) {
                if (question_no <= questionsSize) {
                    // chatList.add(etAnswer.getText().toString().trim());
//                    Log.e("Question", question_no + "");
                    answersList.add(etAnswer.getText().toString().trim());
                    chatList.add(new Message(etAnswer.getText().toString().trim(), 2));
                    rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
                    rvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            rvChat.smoothScrollToPosition(chatList.size() - 1);
                        }
                    });


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject questionJsonObject = null;
                            try {
                                //chatList.add(questionList.getString(Constants.Keys.QUESTION_ + (++question_no)));
                                questionJsonObject = questionJSONArray.getJSONObject(question_no++);
                                chatList.add(new Message(questionJsonObject.getString("question"), 1));
                                //chatList.add(new Message(questionList.getString(Constants.Keys.QUESTION_ + (++question_no)), 1));
                                if (questionJsonObject.getString("type").equalsIgnoreCase("mcq")){
                                    try {
                                        //chatList.add(new Message(questionJsonObject.getJSONArray("options"), 15));
                                        Message message = new Message(questionJsonObject.getJSONArray("options"), 15);
                                        updateListWithDelay(message);
                                        etAnswer.setEnabled(false);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    etAnswer.setEnabled(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            rvChat.getAdapter().notifyDataSetChanged();
                            rvChat.post(new Runnable() {
                                @Override
                                public void run() {
                                    rvChat.smoothScrollToPosition(chatList.size() - 1);
                                    if (question_no > questionsSize)
                                        setupReviewChat();
                                }
                            });

                        }
                    }, 1000);
                    etAnswer.setText("");

                    //if (question_no > questionsSize) {
                    //Toast.makeText(this, "You have successfully answered all questions.", Toast.LENGTH_LONG).show();
                    //postPersonalData();
                        /*new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (question_no > questionsSize)
                                    setupReviewChat();
                            }
                        }, 1000);*/
                    // Utils.hideKeyboard(this);
                    //}
                } else {
                    etAnswer.setText("");
                    Toast.makeText(this, "You have successfully answered all questions.", Toast.LENGTH_LONG).show();
                }
                //rvChat.getAdapter().notifyDataSetChanged();

            }
        }
    }

    public void addAnotherJob() {
        //chatList.add(new Message(personalQuestions[16], 1));
        updateListWithDelay(new Message(personalQuestions[16], 1));
        //chatList.add(new Message("", 8));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateListWithDelay(new Message("", 8));
            }
        }, 2000);
        Utils.hideKeyboard(this);
        /*rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });*/
        etAnswer.setText("");
    }

    public void goToAboutUs(View view) {
        startActivity(new Intent(this, AboutActivity.class));
        rotate(view);
    }

    public void goToFAQ(View view) {
        startActivity(new Intent(this, FaqActivity.class));
        rotate(view);
    }

    private void setupPersonalInfoChat() {

        if (i < personalQuestions.length) {
            chatList.add(new Message(personalQuestions[i++], 1));
            i = 3;
            //chatList.add(new Message(personalQuestions[i++], 1, "2"));
            chatList.add(new Message(personalQuestions[i], 1, "2"));
            chatList.add(new Message(personalQuestions[6], 1, "2"));
            i = 7;
            updateList();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateListWithDelay(new Message("", 4));
                }
            }, 1500);
            Utils.hideKeyboard(this);
        }
        rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });
    }

    private void setupReviewChat() {

        if (j < reviewQuestions.length) {
            ///chatList.add(new Message(reviewQuestions[j++], 1));
            updateListWithDelay(new Message(reviewQuestions[j++], 1));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //chatList.add(new Message("", 11));
                    updateListWithDelay(new Message("", 11));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatList.add(new Message(reviewQuestions[j++], 1));
                            //chatList.add(new Message("", 12));
                            updateList();
                            updateListWithDelay(new Message("", 12));
                        }
                    }, 3000);
                }
            }, 1000);


        }
        /*rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });*/
    }

    public void submitInfo() {

       // chatList.add(new Message("", 14));
        updateListWithDelay(new Message("", 14));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatList.add(new Message(reviewQuestions[reviewQuestions.length - 1], 1));
                updateList();
                //chatList.add(new Message("", 13));
                updateListWithDelay(new Message("", 13));
            }
        }, 2000);
        /*rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });*/
    }

    public void askToAddAnotherJob() {

       // chatList.add(new Message(personalQuestions[17], 1));
        updateListWithDelay(new Message(personalQuestions[17], 1));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatList.add(new Message("", 10));
                Utils.hideKeyboard(ChatActivity.this);
                updateList();
            }
        }, 2000);
        /*rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });*/
    }

    public void addSocialAgain(final int type) {

        if (type == 1) {
            chatList.add(new Message("Great! LinkedIn has been connected.", 1));
        } else
            chatList.add(new Message("Great! Facebook has been connected.", 1));
        updateListWithDelay();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatList.add(new Message("", 7, type + ""));
                updateList();
            };
        }, 1500);
        /*chatList.add(new Message("", 7, type + ""));
        rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });*/
    }

    public void addAfterSocialLoginn(int type) {

        if (type == 1) {
            chatList.add(new Message("Great! LinkedIn has been connected.", 1));
        } else
            chatList.add(new Message("Great! Facebook has been connected.", 1));
        chatList.add(new Message(personalQuestions[i++], 1));
        updateListWithDelay();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatList.add(new Message(personalQuestions[i++], 1, "2"));
                updateList();
                chatList.add(new Message("", 5));
                updateListWithDelay();
                Utils.hideKeyboard(ChatActivity.this);
            }
        }, 2000);
        /*chatList.add(new Message(personalQuestions[i++], 1, "2"));
        chatList.add(new Message("", 5));
        Utils.hideKeyboard(this);
        rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });*/
        socialLogin = true;
    }

    public void getImage(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Constants.PROFILE_PIC = result.getUri();
                rvChat.getAdapter().notifyDataSetChanged();
                //imageView.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 3672) {

            LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void openSelectSkillsDialog() {

        final Dialog dialog = new Dialog(this); // Context, this, etc.
        LayoutInflater inflater = getLayoutInflater();
        //View layout = inflater.inflate(R.layout.dialog_thanks,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_skills);
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;
        rvSelectedSkills = (RecyclerView) dialog.findViewById(R.id.rv_selected_skills);
        skillsList = new ArrayList<>();
        skillsList.add("Java");
        skillsList.add("Android");
        skillsList.add("C++");
        skillsList.add("Objective C");
        skillsList.add("Algorithm");
        skillsList.add("NodeJS");
        skillsList.add("Angular");
        skillsList.add("MongoDB");
        skillsList.add("CodeIgnitor");
        skillsList.add("Kotlin");
        skillsList.add("Swift4");
        skillsList.add("Developement");
        skillsList.add("Engineer");

        selectedSkills = new ArrayList<>();

        final AutoCompleteTextView etSkills = (AutoCompleteTextView) dialog.findViewById(R.id.et_skills);
        etSkills.setAdapter(new ArrayAdapter<String>(ChatActivity.this,
                android.R.layout.simple_list_item_1, skillsList));

        etSkills.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                selectSkill(skillsList.get(skillsList.indexOf(etSkills.getText().toString().trim())));
                etSkills.setText("");

            }
        });

        ChipsLayoutManager spanLayoutManager1 = ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER_DENSE)
                .build();
        rvSelectedSkills.setLayoutManager(spanLayoutManager1);
        rvSelectedSkills.addItemDecoration(new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_space),
                getResources().getDimensionPixelOffset(R.dimen.item_space)));
        rvSelectedSkills.setAdapter(new SelectedSkillsAdapter(this, selectedSkills));

        RecyclerView rvSkills = (RecyclerView) dialog.findViewById(R.id.rv_skills);
        ChipsLayoutManager spanLayoutManager = ChipsLayoutManager.newBuilder(this)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER_DENSE)
                .build();
        rvSkills.setLayoutManager(spanLayoutManager);
        rvSkills.addItemDecoration(new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_space),
                getResources().getDimensionPixelOffset(R.dimen.item_space)));
        rvSkills.setAdapter(new SkillsAdapter(this, skillsList));


        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_done);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                /*etAnswer.setText("Skills added.");
                addAnswerToChat(v);*/
                addListToChat();
            }
        });

        ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                etAnswer.setText("No skills added.");
                addAnswerToChat(v);
            }
        });
        dialog.show();

    }

    public void selectSkill(String skill) {
        if (!selectedSkills.contains(skill)) {
            selectedSkills.add(skill);
            rvSelectedSkills.getAdapter().notifyDataSetChanged();
        }
    }

    public void removeSkill(String skill) {
        selectedSkills.remove(skill);
        rvSelectedSkills.getAdapter().notifyDataSetChanged();
    }

    public void openAadharNumberDialog() {


        final Dialog dialog = new Dialog(this); // Context, this, etc.
        LayoutInflater inflater = getLayoutInflater();
        //View layout = inflater.inflate(R.layout.dialog_thanks,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_aadhar_number);
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;

        dialog.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verified) {
                    ViewAnimationUtils.collapse(dialog.findViewById(R.id.aadhar_lay));
                    ViewAnimationUtils.expand(dialog.findViewById(R.id.verified_lay));
                    Utils.hideKeyboard(ChatActivity.this);
                } else {
                    ViewAnimationUtils.collapse(dialog.findViewById(R.id.tv_lable));
                    ViewAnimationUtils.expand(dialog.findViewById(R.id.otp_lay));
                    verified = true;
                }

            }
        });

        dialog.findViewById(R.id.tv_submit_verified).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimationUtils.collapse(dialog.findViewById(R.id.verified_lay));
                ViewAnimationUtils.expand(dialog.findViewById(R.id.completed_lay));
                ((TextView) dialog.findViewById(R.id.tv_aadhar)).setText(((EditText) dialog.findViewById(R.id.et_aadhar)).getText().toString().trim());

            }
        });

        dialog.findViewById(R.id.tv_submit_completed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verified = false;
                etAnswer.setText(getResources().getString(R.string.aadhar_added));
                addAnswerToChat(v);
                dialog.dismiss();

            }
        });

        dialog.show();

    }

    public void openResultDialog(String message, int number) {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        LayoutInflater inflater = getLayoutInflater();
        //View layout = inflater.inflate(R.layout.dialog_thanks,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_score);
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;

        ((TextView) dialog.findViewById(R.id.tv_username)).setText(FirstName + " " + LastName);
        ((TextView) dialog.findViewById(R.id.tv_score)).setText(number + "");
        ((TextView) dialog.findViewById(R.id.tv_social_accounts)).setText(socialAccounts + "");

        try {
            if (Constants.PROFILE_PIC != null) {
                ((CircleImageView) dialog.findViewById(R.id.iv_profile)).setImageURI(Constants.PROFILE_PIC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (PROCEED) {
            ((TextView) dialog.findViewById(R.id.tv_submit_verified)).setText(getResources().getString(R.string.text_proceed));
        }

        dialog.findViewById(R.id.tv_submit_verified).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PROCEED) {
                    dialog.dismiss();
                    openThanksDialog();
                } else {
                    PROCEED = true;
                    postPersonalData();
                    dialog.dismiss();
                }

            }
        });


        dialog.show();

    }

    public void openThanksDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        LayoutInflater inflater = getLayoutInflater();
        //View layout = inflater.inflate(R.layout.dialog_thanks,null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_thanks);
        dialog.getWindow().getAttributes().width = LinearLayout.LayoutParams.MATCH_PARENT;

        ((TextView) dialog.findViewById(R.id.amount_req_label)).setText(LOAN_NAME);
        ((TextView) dialog.findViewById(R.id.tv_amount_requested)).setText("₹ " + requestedAmount);
        ((TextView) dialog.findViewById(R.id.tv_monthly_payment)).setText("₹ " + emi);
        ((TextView) dialog.findViewById(R.id.tv_amount_passed)).setText("₹ " + requestedAmount);

        ((RelativeLayout) dialog.findViewById(R.id.main_lay)).setBackgroundResource(image);

        dialog.show();

    }

    private void addListToChat() {
        if (i < personalQuestions.length) {
            chatList.add(new Message(selectedSkills, 9));
            updateList();
            if (i == 15) {
                chatList.add(new Message(personalQuestions[i++], 1, "clickable"));
                updateList();
                etAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
                Utils.hideKeyboard(this);
            } else if (i == 14 || i == 13) {
                chatList.add(new Message(personalQuestions[i++], 1));
                updateList();
                etAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
                //chatList.add(new Message(personalQuestions[i++], 1));
                updateListWithDelay(new Message(personalQuestions[i++], 1));
                etAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
            }
            if (i == 4 || i == 10 || i == 12) {
                chatList.add(new Message(personalQuestions[i++], 1, "2"));
                updateList();
            }
            if (i == 7) {
                chatList.add(new Message("", 4));
                updateList();
                Utils.hideKeyboard(this);
            }
            if (i == 8) {
                chatList.add(new Message("", 7, "2"));
                updateList();
                chatList.add(new Message(personalQuestions[i++], 1, "2"));
                updateListWithDelay();
                Utils.hideKeyboard(this);
            }
            if (i == 11) {
                chatList.add(new Message("", 5));
                updateList();
                Utils.hideKeyboard(this);
            }
            if (i == 13) {
                chatList.add(new Message("", 6));
                updateList();
                Utils.hideKeyboard(this);
            }
            if (i == 17) {
                //chatList.add(new Message("", 8));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateListWithDelay(new Message("", 8));
                    }
                }, 1000);
                //updateList();
                Utils.hideKeyboard(this);
            }
           /* rvChat.getAdapter().notifyItemInserted(chatList.size() - 1);
            rvChat.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rvChat.smoothScrollToPosition(chatList.size() - 1);
                }
            }, 1000);*/
            etAnswer.setText("");

            if (question_no > questionsSize) {
                setupChat();
                Utils.hideKeyboard(this);
            }
        }
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
        return jsonArray;
    }

    public void initFBLogin() {
        //callbackManager = CallbackManager.Factory.create();
        //////////////////////////////////FACEBOOK LOGIN==start
        FacebookSdk.sdkInitialize(this);
        AccessToken fbAccessToken;
        //get current token

        List<String> permissionNeeds = Arrays.asList("public_profile", "email", "user_friends", "user_birthday");
        LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                com.facebook.Profile.fetchProfileForCurrentAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                                try {
                                    ///get facebook profile data
                                    /*String email = jsonObject.getString("email");
                                    System.out.println("fbEmail: " + email);
                                    String name = jsonObject.getString("name");
                                    System.out.println("fbName: " + name);


                                    //String birthday = jsonObject.getString("birthday");
                                    //System.out.println("birthday: " + birthday);
                                    String id = jsonObject.getString("id");
                                    URL imageURL = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                                    //String photo = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                                    String access_token;

                                    AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
                                    access_token = currentAccessToken.getToken();

                                    Profile profile = Profile.getCurrentProfile();
                                    if (Profile.getCurrentProfile() != null) {
                                        Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                                    }*/
                                    fbObject = jsonObject;

                                    LoginManager.getInstance().logOut();
                                    FB_LOGIN = true;
                                    socialAccounts++;
                                    //socialLogin = true;
                                    if (!LINKED_IN) {
                                        addSocialAgain(2);
                                    } else {
                                        addAfterSocialLoginn(2);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //etAnswer.setText("Fb Info added!");
                                //addAnswerToChat(null);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
//                AccessToken.setCurrentAccessToken(null);
                // Utils.showToast(mActivity, "FB login cancel");
            }

            @Override
            public void onError(FacebookException e) {
                // Utils.showToast(mActivity, e.toString());
//                AccessToken.setCurrentAccessToken(null);
            }
        });
    }

    public void setGender(String gender) {
        this.Gender = gender;
        etAnswer.setText(gender);
        addAnswerToChat(null);
    }

    public void setMarriageStatus(String status) {
        this.MaritalStatus = status;
        etAnswer.setText(status);
        addAnswerToChat(null);
    }

    private void checkSMSPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS},
                        MY_SMS_REQUEST_CODE);
            } else {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                getSMS();
            }
        } else {
            // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            getSMS();
        }
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_SMS_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                getSMS();

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    public void linkedInLogin() {
        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope()//pass the build scope here
                , new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        // Authentication was successful. You can now do
                        // other calls with the SDK.
                        // Toast.makeText(ChatActivity.this, "Successfully authenticated with LinkedIn.", Toast.LENGTH_SHORT).show();
                        fetchBasicProfileData();
                        LINKED_IN = true;
                        socialAccounts++;
                        //socialLogin = true;
                        if (!FB_LOGIN) {
                            addSocialAgain(1);
                        } else {
                            addAfterSocialLoginn(2);
                        }
                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        // Handle authentication errors
//                        Log.e("LinkedIn", "Auth Error :" + error.toString());
                        Toast.makeText(ChatActivity.this, "Failed to authenticate with LinkedIn. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }, true);//if TRUE then it will show dialog if
        // any device has no LinkedIn app installed to download app else won't show anything
    }

    private void fetchBasicProfileData() {

        //In URL pass whatever data from user you want for more values check below link
        //LINK : https://developer.linkedin.com/docs/fields/basic-profile
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,public-profile-url,picture-url,email-address,picture-urls::(original))";
        //String url = "https://api.linkedin.com/v2/me";

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {
                // Success!
                linkedInData = apiResponse.getResponseDataAsJson();
//                Log.e("linkedInData", linkedInData.toString());
                //Log.d(TAG, "API Res : " + apiResponse.getResponseDataAsString() + "\n" + apiResponse.getResponseDataAsJson().toString());
                //Toast.makeText(ChatActivity.this, "Successfully fetched LinkedIn profile data.", Toast.LENGTH_SHORT).show();

                //update UI on successful data fetched
                doLogout(null);
            }

            @Override
            public void onApiError(LIApiError liApiError) {
                // Error making GET request!
//                 Log.e("linkedInData", "Fetch profile Error   :" + liApiError.getLocalizedMessage());
                Toast.makeText(ChatActivity.this, "Failed to fetch basic profile data. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(ApiResponse apiResponse) {
        try {
            if (apiResponse != null) {
                JSONObject jsonObject = apiResponse.getResponseDataAsJson();

                //display user basic details
                // userDetails.setText("Name : " + jsonObject.getString("firstName") + " " + jsonObject.getString("lastName") + "\nHeadline : " + jsonObject.getString("headline") + "\nEmail Id : " + jsonObject.getString("emailAddress"));

                //use the below string value to display small profile picture
                String smallPicture = jsonObject.getString("pictureUrl");

                //use the below json parsing for different profile pictures and big size images
                JSONObject pictureURLObject = jsonObject.getJSONObject("pictureUrls");
                if (pictureURLObject.getInt("_total") > 0) {
                    //get array of picture urls
                    JSONArray profilePictureURLArray = pictureURLObject.getJSONArray("values");
                    if (profilePictureURLArray != null && profilePictureURLArray.length() > 0) {
                        // get 1st image link and display using picasso
                       /* Picasso.with(this).load(profilePictureURLArray.getString(0))
                                .placeholder(R.mipmap.ic_launcher_round)
                                .error(R.mipmap.ic_launcher_round)
                                .into(userImageView);*/
                    }
                } else {
                    // if no big image is available then display small image using picasso
                    /*Picasso.with(this).load(smallPicture)
                            .placeholder(R.mipmap.ic_launcher_round)
                            .error(R.mipmap.ic_launcher_round)
                            .into(userImageView);*/
                }

                //show hide views

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doLogout(View view) {
        //clear session on logout click
        LISessionManager.getInstance(this).clearSession();

        //show hide views
        //show toast
        //Toast.makeText(this, "Logout successfully.", Toast.LENGTH_SHORT).show();
    }

    public void addIndustryDetail(String industry, String job_title, String company_name, String start_date,
                                  String end_date, String current) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("industry", industry);
            jsonObject.put("job_title", job_title);
            jsonObject.put("company_name", company_name);
            jsonObject.put("start_date", start_date);
            jsonObject.put("end_date", end_date);
            jsonObject.put("current", current);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        industryJsonArray.put(jsonObject);

        Constants.jobsArray = industryJsonArray;

    }

    public void checkJobs() {
        startActivity(new Intent(this, JobsActivity.class).putExtra("image", image));
    }

    private void updateList() {
        rvChat.getAdapter().notifyDataSetChanged();
        rvChat.post(new Runnable() {
            @Override
            public void run() {
                rvChat.smoothScrollToPosition(chatList.size() - 1);
            }
        });
    }

    private void updateListWithDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvChat.getAdapter().notifyDataSetChanged();
                rvChat.post(new Runnable() {
                    @Override
                    public void run() {
                        rvChat.smoothScrollToPosition(chatList.size() - 1);
                    }
                });
            }
        }, 1000);
    }

    private void updateListWithDelay(final Message message) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatList.add(message);
                rvChat.getAdapter().notifyDataSetChanged();
                rvChat.post(new Runnable() {
                    @Override
                    public void run() {
                        rvChat.smoothScrollToPosition(chatList.size() - 1);
                    }
                });
            }
        }, 1300);
    }


    public String getSelectedLanguage(Context myContext) {
        SharedPreferences prefs = myContext.getSharedPreferences(Constants.Locale_Preference, Activity.MODE_PRIVATE);
        String restoredText = prefs.getString(Constants.SELECTED_LANG, null);
        if (restoredText != null) {
            return restoredText;
        }
        else {
            return "en";
        }
    }

    public void getQuestions(Context ctx, CustomResponseListener crl,String reqCode){
        try {
            new Communicator().post(ctx, reqCode, Constants.GET_QUESTIONS, new StringEntity(""), crl);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void calculateScore(String CustomerNumber,String FirstName,String LastName,String PersonalNumber,String PhoneNumber,String CellNumber,String DateOfBirth,String EmailAddress,String Gender,String MaritalStatus,String Education,String AboutMe,String loan_type,int requested_amount,int monthly_repayment_amount,int loan_duration,Context ctx ,CustomResponseListener crl,String reqCode){
        JSONObject jsonCustomer = new JSONObject();
        JSONObject jsonLoan = new JSONObject();
        JSONObject jsonData = new JSONObject();
        try {
            jsonCustomer.put("CustomerNumber", CustomerNumber);
            jsonCustomer.put("FirstName", FirstName);
            jsonCustomer.put("LastName", LastName);
            jsonCustomer.put("PersonalNumber", PersonalNumber);
            jsonCustomer.put("PhoneNumber", PhoneNumber);
            jsonCustomer.put("CellNumber", CellNumber);
            jsonCustomer.put("DateOfBirth", DateOfBirth);
            jsonCustomer.put("EmailAddress", EmailAddress);
            jsonCustomer.put("Gender", Gender);
            jsonCustomer.put("MaritalStatus", MaritalStatus);
            jsonCustomer.put("Education", Education);
            jsonCustomer.put("AboutMe", AboutMe);
            jsonLoan.put("loan_type", loan_type);
            jsonLoan.put("requested_amount", requested_amount);
            jsonLoan.put("monthly_repayment_amount", monthly_repayment_amount);
            jsonLoan.put("loan_duration", loan_duration);
            jsonCustomer.put("loan_request", jsonLoan);
            jsonData.put("customer", jsonCustomer);
            StringEntity entity = new StringEntity(jsonData.toString());
            new Communicator().post(ctx, reqCode, Constants.GET_SCORES, entity, crl);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
