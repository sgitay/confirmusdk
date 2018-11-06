package com.confirmu.up.api;


import android.net.Uri;

import org.json.JSONArray;

/**
 * Created by admin on 14-Oct-17.
 */

public class Constants {

    private static String BASE_URL = "https://api.confirmu.com/api/";
    //    private static String BASE_URL = "http://confirmu-test.us-east-2.elasticbeanstalk.com/api/";
    public static String GET_QUESTIONS = BASE_URL + "getquestions/";
    public static String SMS = BASE_URL + "sms";
    public static String GET_SCORES = BASE_URL + "getscores/";
    public static String SELECTED_LANG = "selected_language";
    public static final String Locale_Preference = "Locale Preference";
    public static final String Locale_KeyValue = "Saved Locale";

    public static Uri PROFILE_PIC;
    public static JSONArray smsArray = new JSONArray();
    public static JSONArray locationArray;
    public static JSONArray jobsArray;
    public static JSONArray contactsArray;
    public static JSONArray callLogsArray;
    public static String name = "f n";
    public static String email = "";
    public static String phone = "";

    //For library
    public static String Access_Token = "0662aba4-555a-41ea-b6c2-d2644d7616df";
    public static String Partner = "mobileapp";

    public static class Keys{
        public static final String LOAN_NAME = "loan_name";
        public static String LOAN_TYPE = "loan_type";
        public static String QUESTIONS = "Questions";
        public static String CAR = "car";
        public static String HOME_LOAN = "homeloan";
        public static String WEDDING = "wedding";
        public static String WHEELERS = "wheelers";
        public static String QUESTION_ = "question_";
    }
}
