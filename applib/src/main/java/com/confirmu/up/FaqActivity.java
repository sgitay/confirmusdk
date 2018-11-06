package com.confirmu.up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.confirmu.R;

public class FaqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
    }

    public void back(View view){
        onBackPressed();
    }
}
