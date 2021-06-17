package com.jonahmiddleton.rectanglecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements OnEditorActionListener {
    private EditText widthEditText;
    private EditText heightEditText;
    private TextView areaTextView;
    private TextView perimeterTextView;

    private SharedPreferences savedValues;
    private  DecimalFormat twoDigitDecimal = new DecimalFormat("0.00");

    private float width = 0.00f;
    private float height = 0.00f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widthEditText = (EditText)findViewById(R.id.widthEditText);
        heightEditText = (EditText)findViewById(R.id.heightEditText);
        areaTextView = (TextView)findViewById(R.id.areaTextView);
        perimeterTextView = (TextView)findViewById(R.id.perimeterTextView);

        widthEditText.setOnEditorActionListener(this);
        heightEditText.setOnEditorActionListener(this);

        savedValues = getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = savedValues.edit();

        editor.putFloat("width", width);
        editor.putFloat("height", height);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        width = savedValues.getFloat("width", 0.00f);
        height = savedValues.getFloat("height", 0.00f);
        widthEditText.setText(twoDigitDecimal.format(width));
        heightEditText.setText(twoDigitDecimal.format(height));
        calculateAndDisplay();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }
        return false;
    }

    public void calculateAndDisplay(){
        width = Float.parseFloat(widthEditText.getText().toString());
        height = Float.parseFloat(heightEditText.getText().toString());

        float area = width * height;
        float perimeter = 2 * width + 2 * height;

        areaTextView.setText(twoDigitDecimal.format(area));
        perimeterTextView.setText(twoDigitDecimal.format(perimeter));
    }
}