package com.confirmu.up;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.confirmu.R;
import com.confirmu.up.api.Constants;

import java.util.Locale;

public class LangActivity extends AppCompatActivity implements View.OnClickListener {
    private static Button english, hindi, spanish, bahasa;
    private static TextView chooseText;
    private static Locale myLocale;

    //Shared Preferences Variables
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lang);
        initViews();
        setListeners();
//        loadLocale();
    }


    //Initiate all views
    private void initViews() {
        sharedPreferences = getSharedPreferences(Constants.Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        chooseText = (TextView) findViewById(R.id.choose_text);
        english = (Button) findViewById(R.id.english);
        hindi = (Button) findViewById(R.id.hindi);
        spanish = (Button) findViewById(R.id.spanish);
        bahasa = (Button) findViewById(R.id.bahasa);
//        russian = (Button) findViewById(R.id.russian);
    }

    //Set Click Listener
    private void setListeners() {
        english.setOnClickListener(this);
        hindi.setOnClickListener(this);
        spanish.setOnClickListener(this);
        bahasa.setOnClickListener(this);
//        russian.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String lang = "en";//Default Language
        int i = view.getId();
        if (i == R.id.english) {
            lang = "en";

        } else if (i == R.id.hindi) {
            lang = "hi";

        } else if (i == R.id.spanish) {
            lang = "es";

        } else if (i == R.id.bahasa) {
            lang = "in";

//            case R.id.russian:
//                lang = "ru";
//                break;
        }

        changeLocale(lang);//Change Locale on selection basis
    }


    //Change Locale
    public void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);//Set Selected Locale
        saveLocale(lang);//Save the selected locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
        //updateTexts();//Update texts according to locale
        callIntroActivity();
    }

    public void callIntroActivity() {
        Intent intent=new Intent(LangActivity.this,IntroActivity.class);
        startActivityForResult(intent, 2);
    }

    //Save locale method in preferences
    public void saveLocale(String lang) {
        editor.putString(Constants.SELECTED_LANG, lang);
        editor.commit();
    }

    //Get locale method in preferences
    public void loadLocale() {
        String language = sharedPreferences.getString(Constants.Locale_KeyValue, "");
        changeLocale(language);
    }

    //Update text methods
    private void updateTexts() {
        chooseText.setText(R.string.select_lang_text);
        english.setText(R.string.btn_en);
        hindi.setText(R.string.btn_hi);
//        russian.setText(R.string.btn_ru);
//        french.setText(R.string.btn_fr);
//        german.setText(R.string.btn_de);
    }


}
