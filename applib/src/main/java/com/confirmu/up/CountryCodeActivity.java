package com.confirmu.up;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;

import com.confirmu.R;
import com.confirmu.up.adapters.CountryCodeAdapter;
import com.confirmu.up.model.CountryCode;
import com.confirmu.up.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 10/14/2017.
 */

public class CountryCodeActivity extends Activity implements View.OnClickListener {

    Activity activity;
    RecyclerView recycler_view;

    ArrayList<CountryCode> list;
    List<CountryCode> countryCodes;
    EditText etSearch;

    private Animation animShow, animHide, newShow;
    CountryCodeAdapter contactsAdapter;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_code);


        setinitialElements();

    }

    private void setinitialElements() {
        activity = this;

        etSearch = (EditText) findViewById(R.id.et_search);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contactsAdapter.getFilter().filter(charSequence.toString());
                /*if (charSequence.toString().length() > 0) {
                    clearLayout.setVisibility(View.VISIBLE);
                } else {
                    clearLayout.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        View btBack = findViewById(R.id.btn_navigation);
        ImageView ivSearch = (ImageView) findViewById(R.id.btn_search);

        btBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);


        getCountryCodes();

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(CountryCodeActivity.this, LinearLayoutManager.VERTICAL, false));
        contactsAdapter = new CountryCodeAdapter(CountryCodeActivity.this, countryCodes, recycler_view);
        recycler_view.setAdapter(contactsAdapter);
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
        //setupSpinner(countryCodes);
    }


    public void goToBack(View view) {
        Utils.hideKeyboard(activity);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void clearSearchBox(View view) {
        etSearch.setText("");
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_navigation) {
            onBackPressed();

        } else if (i == R.id.btn_search) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = findViewById(viewID);

        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material));
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 200);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();


    }
    public void setCountryCode(String countriesName, String code){

        Intent intent = new Intent();
        intent.putExtra("CODE", code);
        intent.putExtra("NAME", countriesName);
        setResult(1, intent);
        finish();

    }

}
