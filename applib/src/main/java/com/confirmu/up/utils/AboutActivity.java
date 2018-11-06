package com.confirmu.up.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.confirmu.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

    }

    public void back(View view){
        onBackPressed();
    }

    public void fb(View view) {
        //startActivity(new Intent(this, VisitUsActivity.class));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.facebook.com/ConfirmU-104511497047643/?ref=content_filter"));
        startActivity(browserIntent);
    }

    public void linkedIn(View view) {
        //startActivity(new Intent(this, TAndCActivity.class));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/confirmu/"));
        startActivity(browserIntent);
    }
    public void twitter(View view) {
        //startActivity(new Intent(this, TAndCActivity.class));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/confirmu2"));
        startActivity(browserIntent);
    }
}
