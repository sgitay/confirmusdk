package com.confirmu.up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.confirmu.R;
import com.confirmu.up.adapters.JobsAdapter;
import com.confirmu.up.api.Constants;

import org.json.JSONObject;

import java.util.StringTokenizer;

public class JobsActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        init();
    }

    private void init() {
        RecyclerView rvJobs = (RecyclerView) findViewById(R.id.rv_jobs);
        rvJobs.setNestedScrollingEnabled(false);
        rvJobs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvJobs.setAdapter(new JobsAdapter(this, Constants.jobsArray));

        setHeaderData();


    }
    public void back(View view){
        onBackPressed();
    }

    private void setHeaderData(){
        int startYear = 2018, endYear = 2018;
        String startDate , endDate;
        try {
            JSONObject jsonObject = Constants.jobsArray.getJSONObject(0);
            startDate = jsonObject.getString("start_date");

            JSONObject jsonObjectEnd = Constants.jobsArray.getJSONObject(Constants.jobsArray.length() - 1);
            endDate = jsonObjectEnd.getString("end_date");
            if (endDate.equals("")){
                endYear = 2018;
            }else {
                StringTokenizer stringTokenizerEnd = new StringTokenizer(endDate, ".");
                String endMonth = stringTokenizerEnd.nextToken();
                String endYearSt = stringTokenizerEnd.nextToken();
                endYear = Integer.parseInt(endYearSt);
            }

            StringTokenizer stringTokenizerStart = new StringTokenizer(startDate, ".");
            String startMonth = stringTokenizerStart.nextToken();
            String startYearSt = stringTokenizerStart.nextToken();
            startYear = Integer.parseInt(startYearSt);


        } catch (Exception e) {
            e.printStackTrace();
        }

        TextView tvYears = (TextView)findViewById(R.id.tv_years);
        tvYears.setText((endYear - startYear) + "");

        ImageView imageView = (ImageView)findViewById(R.id.iv_loan_image);
        imageView.setImageResource(getIntent().getIntExtra("image", 0));
    }

}
