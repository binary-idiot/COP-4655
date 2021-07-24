package com.jonahmiddleton.players;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private DBHelper dbHelper;

    EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        nameEditText = (EditText)findViewById(R.id.nameEditText);
        nameEditText.setOnEditorActionListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId){
            case EditorInfo.IME_ACTION_DONE:
                addPlayer();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void addPlayer() {
        String name = String.valueOf(nameEditText.getText());
        boolean b = PlayerData.addPlayer(dbHelper, name);
        String toastMessage = "Player '" + name + "' added";
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        finish();
    }
}