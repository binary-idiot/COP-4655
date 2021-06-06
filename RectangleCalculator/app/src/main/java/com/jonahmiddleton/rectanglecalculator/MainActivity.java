package com.jonahmiddleton.rectanglecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText widthEditText;
    private EditText heightEditText;
    private TextView areaTextView;
    private TextView perimeterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widthEditText = (EditText)findViewById(R.id.widthEditText);
        heightEditText = (EditText)findViewById(R.id.heightEditText);
        areaTextView = (TextView)findViewById(R.id.areaTextView);
        perimeterTextView = (TextView)findViewById(R.id.perimeterTextView);

        setContentView(R.layout.activity_main);
    }


}