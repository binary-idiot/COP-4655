package com.jonahmiddleton.measurementconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher {

    public static final String PREFS = "measurementconverterstate";

    Spinner conversionTypeSpinner;
    TextView fromLabel;
    EditText fromView;
    TextView toLabel;
    TextView toView;

    SharedPreferences appState;

    List<ConversionType> conversionTypes;

    ConversionType currentConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionTypeSpinner = (Spinner)findViewById(R.id.conversionTypeSpinner);
        fromLabel = (TextView)findViewById(R.id.fromLabel);
        fromView = (EditText)findViewById(R.id.fromView);
        toLabel = (TextView)findViewById(R.id.toLabel);
        toView = (TextView) findViewById(R.id.toView);

        appState = getSharedPreferences(PREFS, Context.MODE_PRIVATE);

        conversionTypeSpinner.setOnItemSelectedListener(this);
        fromView.addTextChangedListener(this);

        conversionTypes = new ArrayList<ConversionType>();
        conversionTypes.add(new ConversionType(getString(R.string.mileUnit), getString(R.string.kilometerUnit), Double.parseDouble(getString(R.string.mToK))));
        conversionTypes.add(new ConversionType(getString(R.string.kilometerUnit), getString(R.string.mileUnit), Double.parseDouble(getString(R.string.kToM))));
        conversionTypes.add(new ConversionType(getString(R.string.inchUnit), getString(R.string.centimeterUnit), Double.parseDouble(getString(R.string.iToC))));
        conversionTypes.add(new ConversionType(getString(R.string.centimeterUnit), getString(R.string.inchUnit), Double.parseDouble(getString(R.string.cToI))));

        ArrayAdapter<ConversionType> typesAdapter = new ArrayAdapter<ConversionType>(this, android.R.layout.simple_spinner_dropdown_item, conversionTypes);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conversionTypeSpinner.setAdapter(typesAdapter);
        conversionTypeSpinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ConversionType type = (ConversionType) parent.getItemAtPosition(position);
        setConversion(type);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        ConversionType type = (ConversionType) parent.getItemAtPosition(0);
        setConversion(type);
    }

    private void setConversion(ConversionType type) {
        currentConversion = type;
        fromLabel.setText(type.from);
        toLabel.setText(type.to);
        calculateAndDisplay();
    }

    private void calculateAndDisplay() {
        String fromValue = (!TextUtils.isEmpty(fromView.getText()))? fromView.getText().toString() : "0";
        double convertedValue = currentConversion.convert(Double.parseDouble(fromValue));
        toView.setText(Double.toString(convertedValue));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        calculateAndDisplay();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}