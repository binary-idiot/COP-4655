package com.jonahmiddleton.players;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreboardActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    ListView scoreboardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        dbHelper = new DBHelper(this);

        scoreboardList = (ListView)findViewById(R.id.scoreboardList);

        ArrayList<Player> players = new ArrayList<Player>(PlayerData.getPlayers(dbHelper));
        PlayerScoreAdapter playerScoreAdapter = new PlayerScoreAdapter(this, R.layout.layout_player_score, players);
        scoreboardList.setAdapter(playerScoreAdapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}