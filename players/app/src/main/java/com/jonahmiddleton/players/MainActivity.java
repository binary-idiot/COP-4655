package com.jonahmiddleton.players;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String MESSAGE = "com.jonahmiddleton.players.MESSAGE";
    public static final String PREFERENCES = "com.jonahmiddleton.players.PREFS";
    public static final String PREF_KEY_PLAYER = "PLAYER";

    Button startButton, scoreboardButton, selectP1Button, selectP2Button, addPButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        scoreboardButton = (Button)findViewById(R.id.scoreboardButton);
        selectP1Button = (Button)findViewById(R.id.selectP1Button);
        selectP2Button = (Button)findViewById(R.id.selectP2Button);
        addPButton = (Button)findViewById(R.id.addPButton);

        startButton.setOnClickListener(new NavigateOnClick(new GameActivity()));
        scoreboardButton.setOnClickListener(new NavigateOnClick(new ScoreboardActivity()));
        selectP1Button.setOnClickListener(new NavigateOnClick(new SelectActivity(), "1"));
        selectP2Button.setOnClickListener(new NavigateOnClick(new SelectActivity(), "2"));
        addPButton.setOnClickListener(new NavigateOnClick(new AddActivity()));
    }

    private class NavigateOnClick implements View.OnClickListener {
        Activity activity;
        String message;

        public NavigateOnClick(Activity newActivity){
            this(newActivity, "");
        }

        public NavigateOnClick(Activity newActivity, String message){
            this.activity = newActivity;
            this.message = message;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, this.activity.getClass());
            intent.putExtra(MESSAGE, this.message);
            startActivity(intent);
        }
    }
}