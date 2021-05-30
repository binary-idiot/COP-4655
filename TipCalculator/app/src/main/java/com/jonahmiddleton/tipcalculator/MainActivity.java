package com.jonahmiddleton.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener, OnClickListener {
    private EditText billAmountEditText;
    private TextView tipTextView;
    private TextView totalTextView;
    private TextView percentTextView;
    private Button percentUpButton;
    private Button percentDownButton;

    private SharedPreferences savedValues;

    private String billAmountString = "";
    private float tipPercent = .15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billAmountEditText = (EditText)findViewById(R.id.billAmountEditText);
        percentTextView = (TextView)findViewById(R.id.percentTextView);
        tipTextView = (TextView)findViewById(R.id.tipTextView);
        totalTextView = (TextView)findViewById(R.id.totalTextView);
        percentUpButton = (Button)findViewById(R.id.percentUpButton);
        percentDownButton = (Button)findViewById(R.id.percentDownButton);

        billAmountEditText.setOnEditorActionListener(this);
        percentDownButton.setOnClickListener(this);
        percentUpButton.setOnClickListener(this);

        savedValues = getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = savedValues.edit();

        editor.putString("billAmountString", billAmountString);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        billAmountString = savedValues.getString("billAmountString", "");
        tipPercent = savedValues.getFloat("tipFloat", 0.15f);
        billAmountEditText.setText(billAmountString);
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }

    public void calculateAndDisplay() {
        billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        if (billAmountString.equals("")) {
            billAmount = 0;
        } else {
            billAmount = Float.parseFloat(billAmountString);
        }

        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.percentDownButton:
                tipPercent -= .01f;
                calculateAndDisplay();
                break;
            case R.id.percentUpButton:
                tipPercent += .01f;
                calculateAndDisplay();
                break;
        }
    }
}