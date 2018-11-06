package com.sqr.emicalcy;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;



import com.confirmu.up.HomeActivity;





public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, AdapterView.OnItemSelectedListener {

    EditText  etMonthlyPayment;
    RadioGroup radioGroup;
    TextView textView, textView1, textView2;
    SeekBar sbAmountRequested;
    Spinner spLoanLength;
    HomeActivity homeActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        radioGroup = findViewById(R.id.radioGroup);
        etMonthlyPayment = findViewById(R.id.etMonthlyPayment);
        spLoanLength = findViewById(R.id.spLoanLength);
        homeActivity = new HomeActivity();
        sbAmountRequested = (SeekBar) findViewById(R.id.seekBar);
        textView.setText("Max limit 100000 and Progress ₹ " + 0);
        textView1.setText("Max limit 100000");
        textView2.setText("Progress 1Month");

        sbAmountRequested.setMax(100000);
        sbAmountRequested.setProgress(0);

        etMonthlyPayment.setText("0");

        sbAmountRequested.setOnSeekBarChangeListener(this);
        spLoanLength.setOnItemSelectedListener(this);
        etMonthlyPayment.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(sbAmountRequested.getProgress()>0 && sbAmountRequested.getProgress()>Float.valueOf(0 + MainActivity.this.etMonthlyPayment.getText().toString())) {

                    int loanLength = homeActivity.getLoanLength(0 + MainActivity.this.sbAmountRequested.getProgress(), 0 + Float.valueOf(0 + MainActivity.this.etMonthlyPayment.getText().toString()));
                    if(loanLength>0 && loanLength <= 36) {
                        MainActivity.this.textView1.setText("Max limit " + sbAmountRequested.getProgress() + " ₹ Progress" + etMonthlyPayment.getText().toString());
                        MainActivity.this.textView2.setText("Progress " + loanLength + " Months");
                        MainActivity.this.spLoanLength.setSelection(loanLength - 1);
                    }

                } else if (sbAmountRequested.getProgress()<Float.valueOf(0 + MainActivity.this.etMonthlyPayment.getText().toString())){
                    etMonthlyPayment.setText(""+sbAmountRequested.getProgress());
                    MainActivity.this.textView1.setText("Max limit " + sbAmountRequested.getProgress() + " ₹ Progress" + etMonthlyPayment.getText().toString());
                    MainActivity.this.textView2.setText("Progress " + 1 + " Months");
                    MainActivity.this.spLoanLength.setSelection(0);
                }
            }
        });


    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int i = seekBar.getId();
        if (i == R.id.seekBar) {
            MainActivity.this.textView.setText("Max limit 100000 and Progress ₹ " + progress);
            int loanLength = homeActivity.getLoanLength(0+progress, 0+Float.valueOf(0+etMonthlyPayment.getText().toString()));

            if(loanLength <= 36 && loanLength > 0)
            {
                MainActivity.this.textView1.setText("Max limit "+sbAmountRequested.getProgress()+" ₹ Progress" + etMonthlyPayment.getText().toString());
                MainActivity.this.textView2.setText("Progress " + loanLength + " Months");
                spLoanLength.setSelection(0+loanLength - 1);
            }


        }

    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if (sbAmountRequested.getProgress() > 0 ) {
            int monthlyRepayment = homeActivity.getMonthlyRepayment(0+sbAmountRequested.getProgress(), position+1);
            MainActivity.this.textView1.setText("Max limit "+sbAmountRequested.getProgress()+" ₹ Progress " + String.valueOf(monthlyRepayment));
            MainActivity.this.etMonthlyPayment.setText(""+monthlyRepayment);
            MainActivity.this.textView2.setText("Progress "+ (position+1) +"Months");
        }
   }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
