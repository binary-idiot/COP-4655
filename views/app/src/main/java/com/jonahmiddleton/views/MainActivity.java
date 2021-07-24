package com.jonahmiddleton.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CheckBox tipCheckBox;
    private RadioButton noRoundRB;
    private RadioButton roundTipRB;
    private RadioButton roundTotalRB;
    private Spinner splitSpinner;
    private SeekBar percentSeekBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tipCheckBox = (CheckBox)findViewById(R.id.tipCheckBox);
        tipCheckBox.setChecked(false);
        if(tipCheckBox.isChecked()){
            Toast.makeText(this, "Is Checked", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Is Not Checked", Toast.LENGTH_SHORT).show();
        }
        
        noRoundRB = (RadioButton)findViewById(R.id.noRoundRB);
        roundTipRB = (RadioButton)findViewById(R.id.roundTipRB);
        roundTotalRB = (RadioButton)findViewById(R.id.roundTotalRB);
        noRoundRB.setChecked(false);
        roundTotalRB.setChecked(true);
        if(noRoundRB.isChecked()){
            Toast.makeText(this, "Button 1 Is Checked", Toast.LENGTH_SHORT).show();
        }else if(roundTipRB.isChecked()){
            Toast.makeText(this, "Button 2 Is Checked", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Button 3 Is Checked", Toast.LENGTH_SHORT).show();
        }

        splitSpinner = (Spinner)findViewById(R.id.splitSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.split_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        splitSpinner.setAdapter(adapter);
        splitSpinner.setSelection(0);
        int position = splitSpinner.getSelectedItemPosition();
        String selectedText = (String)splitSpinner.getSelectedItem();
        Toast.makeText(this, selectedText, Toast.LENGTH_SHORT).show();

        percentSeekBar = (SeekBar)findViewById(R.id.percentSeekBar);
        percentSeekBar.setProgress(20);
        int percent = percentSeekBar.getProgress();
        Toast.makeText(this, "Percent = " + String.valueOf(percent), Toast.LENGTH_SHORT).show();
    }
}