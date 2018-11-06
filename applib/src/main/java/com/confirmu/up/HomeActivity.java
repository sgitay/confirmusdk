package com.confirmu.up;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.confirmu.R;
import com.confirmu.up.adapters.LoansAdapter;
import com.confirmu.up.api.Communicator;
import com.confirmu.up.api.Constants;
import com.confirmu.up.api.CustomResponseListener;
import com.confirmu.up.model.LoanType;
import com.confirmu.up.utils.ViewAnimationUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HomeActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CustomResponseListener {

    CardView cvSpinner;
    boolean spinnerVisible = false;
    SeekBar sbAmountRequested, sbMonthlyPayment, sbLoanLength;
    TextView tvAmountRequested, tvMonthlyPayment, tvLoanLength, tvAmountRequestedHeader, tvMonthlyPaymentHeader, tvLoanLengthHeader;
    RelativeLayout amountLay;
    String key = Constants.Keys.HOME_LOAN;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        cvSpinner = (CardView) findViewById(R.id.cv_spinner);
        RecyclerView rvLoans = (RecyclerView) findViewById(R.id.rv_loans);
        rvLoans.setNestedScrollingEnabled(false);
        rvLoans.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<LoanType> loansList = new ArrayList<>();
        loansList.add(new LoanType(getResources().getString(R.string.wedding_loan), R.mipmap.wedding, Constants.Keys.WEDDING));
        loansList.add(new LoanType(getResources().getString(R.string.home_loan), R.mipmap.home, Constants.Keys.HOME_LOAN));
        loansList.add(new LoanType(getResources().getString(R.string.car_loan), R.mipmap.car, Constants.Keys.CAR));
        loansList.add(new LoanType(getResources().getString(R.string.wheeler_loan), R.mipmap.wheelers, Constants.Keys.WHEELERS));
        rvLoans.setAdapter(new LoansAdapter(this, loansList));

        tvAmountRequested = (TextView) findViewById(R.id.tv_amount_requested);
        tvMonthlyPayment = (TextView) findViewById(R.id.tv_monthly_payment);
        tvLoanLength = (TextView) findViewById(R.id.tv_loan_length);

        tvAmountRequestedHeader = (TextView) findViewById(R.id.tv_amount_requested_header);
        tvMonthlyPaymentHeader = (TextView) findViewById(R.id.tv_monthly_payment_header);
        tvLoanLengthHeader = (TextView) findViewById(R.id.tv_loan_length_header);
        tvLoanLengthHeader.setText("0 "+ getResources().getString(R.string.text_months));

        sbAmountRequested = (SeekBar) findViewById(R.id.sb_amount_req);
        sbMonthlyPayment = (SeekBar) findViewById(R.id.sb_monthly_pay);
        sbLoanLength = (SeekBar) findViewById(R.id.sb_loan_length);


        amountLay = (RelativeLayout) findViewById(R.id.amount_lay);

        sbAmountRequested.setMax(100000);
        sbAmountRequested.setProgress(0);

        sbLoanLength.setMax(36);
        sbLoanLength.setProgress(0);

        sbAmountRequested.setOnSeekBarChangeListener(this);
        sbMonthlyPayment.setOnSeekBarChangeListener(this);
        sbLoanLength.setOnSeekBarChangeListener(this);
    }

    public void toggleSpinner(View view) {
        if (spinnerVisible) {
            ViewAnimationUtils.collapse(cvSpinner);
            spinnerVisible = false;
        } else {
            ViewAnimationUtils.expand(cvSpinner);
            spinnerVisible = true;
        }
    }

    public void closeSpinner(View view) {
        if (spinnerVisible) {
            ViewAnimationUtils.collapse(cvSpinner);
            spinnerVisible = false;
        }
    }


    public void selectLoan(String loanName, int image, String key) {
        ((TextView) findViewById(R.id.tv_finance_name)).setText(loanName);
        ViewAnimationUtils.collapse(cvSpinner);
        spinnerVisible = false;
        amountLay.setBackgroundResource(image);
        this.key = key;
        this.image = image;
    }

    int image = R.mipmap.home;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int i = seekBar.getId();
        if (i == R.id.sb_amount_req) {
            tvAmountRequested.setText("₹ " + progress);
            tvAmountRequestedHeader.setText("₹ " + progress);
            sbMonthlyPayment.setMax(progress);
            tvLoanLength.setText(getLoanLength(progress, sbMonthlyPayment.getProgress()) + " " + getResources().getString(R.string.text_months));
            sbLoanLength.setProgress(getLoanLength(progress, sbMonthlyPayment.getProgress()));
            tvLoanLengthHeader.setText(getLoanLength(progress, sbMonthlyPayment.getProgress()) + " " + getResources().getString(R.string.text_months));

        } else if (i == R.id.sb_monthly_pay) {
            tvMonthlyPayment.setText("₹ " + progress);
            tvMonthlyPaymentHeader.setText("₹ " + progress);
            tvLoanLength.setText(getLoanLength(sbAmountRequested.getProgress(), progress) + " " + getResources().getString(R.string.text_months));
            sbLoanLength.setProgress(getLoanLength(sbAmountRequested.getProgress(), progress));
            tvLoanLengthHeader.setText(getLoanLength(sbAmountRequested.getProgress(), progress) + " " + getResources().getString(R.string.text_months));

        } else if (i == R.id.sb_loan_length) {
            tvLoanLength.setText(progress + " " + getResources().getString(R.string.text_months));
            tvLoanLengthHeader.setText(progress + " " + getResources().getString(R.string.text_months));
            if (sbAmountRequested.getProgress() > 0) {
                int monthlyRepayment = getMonthlyRepayment(sbAmountRequested.getProgress(), progress);
                tvMonthlyPayment.setText(String.format(getString(R.string.r_amount), monthlyRepayment));
                tvMonthlyPaymentHeader.setText(String.format(getString(R.string.r_amount), monthlyRepayment));
                sbMonthlyPayment.setProgress(monthlyRepayment);
            }

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public int getLoanLength(float amountReq, float monthluPay) {
        if (monthluPay <= 100) {
            return 1;
        } else {
            try {
                double length = amountReq / monthluPay;
                //Log.e("Length", length + "");

                if (length % 1 >= 0.5) {
                    length = Math.ceil(length);
                } else {
                    length = Math.floor(length);
                }
                //length = (int)length;
                //Log.e("Length", length + "");
                return (int) length;
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public int getMonthlyRepayment(int amountReq, int loanLength) {
        try {
            return amountReq / loanLength;
        } catch (Exception e) {
            return 0;
        }
    }

    public void goToChat(View view) {
        sms();
//        startActivity(new Intent(this, ChatActivity.class)
//                .putExtra(Constants.Keys.LOAN_TYPE, key));
    }

    private void sms() {

        JSONObject jsonParams = new JSONObject();
        StringEntity entity = null;
        try {
            jsonParams.put("credit_type", key);
            jsonParams.put("loan_amount", sbAmountRequested.getProgress());
            jsonParams.put("duration", sbLoanLength.getProgress());
            jsonParams.put("emi", sbMonthlyPayment.getProgress());
            entity = new StringEntity(jsonParams.toString());
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println("Params : " + jsonParams.toString());
        Communicator communicator = new Communicator();
        communicator.post(this, "1", Constants.SMS, entity, this);
        progressDialog.show();
    }

    @Override
    public void onResponse(String requestCode, String response) {
       // Log.e("onResponse", response);
        progressDialog.dismiss();
        try {
            JSONObject questionsResponseJsonObject = new JSONObject(response);
            if (questionsResponseJsonObject.getBoolean("IsSuccess")) {
                String record = questionsResponseJsonObject.getString("Record");
                //Toast.makeText(this, "" + record, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ChatActivity.class)
                        .putExtra(Constants.Keys.LOAN_TYPE, key)
                        .putExtra(Constants.Keys.LOAN_NAME, ((TextView) findViewById(R.id.tv_finance_name)).getText().toString())
                        .putExtra("loan_amount", sbAmountRequested.getProgress())
                        .putExtra("emi", sbMonthlyPayment.getProgress())
                        .putExtra("duration", sbLoanLength.getProgress())
                        .putExtra("image", image));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String requestCode, Throwable response) {
        //Log.e("onResponse", response.toString());
        progressDialog.dismiss();
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
    }
}
