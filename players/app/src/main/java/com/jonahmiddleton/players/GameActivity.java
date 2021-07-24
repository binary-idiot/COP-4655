package com.jonahmiddleton.players;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.jonahmiddleton.players.PlayerContract.PlayerEntry;

public class GameActivity extends AppCompatActivity {
    private static final String invalidPlayerToastMessage = "Player %d is invalid, please select a player";

    private DBHelper dbHelper;
    private SharedPreferences saveState;
    private HashMap<Integer, Player> players;

    TextView p1Name, p2Name;
    Button p1WinsButton, p2WinsButton, tieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dbHelper = new DBHelper(this);
        saveState = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);
        players = new HashMap<>();

        if(!setSelectedPlayer(1)) return;
        if(!setSelectedPlayer(2)) return;

        p1Name = (TextView)findViewById(R.id.p1Name);
        p2Name = (TextView)findViewById(R.id.p2Name);
        p1WinsButton = (Button)findViewById(R.id.p1WinsButton);
        p2WinsButton = (Button)findViewById(R.id.p2WinsButton);
        tieButton = (Button)findViewById(R.id.tieButton);

        p1Name.setText(players.get(1).getName());
        p2Name.setText(players.get(2).getName());

        p1WinsButton.setOnClickListener(new HandleClick(1));
        p2WinsButton.setOnClickListener(new HandleClick(2));
        tieButton.setOnClickListener(new HandleClick(null));
    }

    public boolean setSelectedPlayer(int playerNum){
        int pID = saveState.getInt(MainActivity.PREF_KEY_PLAYER + playerNum, -1);
        Player player = PlayerData.getPlayer(dbHelper, pID);

        if(player == null){
            Toast.makeText(getApplicationContext(), String.format(invalidPlayerToastMessage, playerNum), Toast.LENGTH_LONG).show();
            finish();
            return false;
        }

        players.put(playerNum, player);
        return true;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private class HandleClick implements View.OnClickListener {
        Integer winner;

        public HandleClick(Integer winner){
            this.winner = winner;
        }

        @Override
        public void onClick(View v) {
            String toastMessage;
            if(winner == null){
                for(Map.Entry entry : players.entrySet()){
                    Player player = (Player)entry.getValue();
                    PlayerData.updatePlayer(dbHelper, player.getId(), PlayerEntry.COLUMN_NAME_TIES, player.getTies() + 1);
                }

                toastMessage = "It's a tie!";
            }else{
                for(Map.Entry entry : players.entrySet()){
                    String updateColumn;
                    int updateValue;
                    Player player = (Player)entry.getValue();

                    if(entry.getKey() == winner){
                        updateColumn = PlayerEntry.COLUMN_NAME_WINS;
                        updateValue = player.getWins() + 1;
                    }else{
                        updateColumn = PlayerEntry.COLUMN_NAME_LOSSES;
                        updateValue = player.getLosses() + 1;
                    }

                    PlayerData.updatePlayer(dbHelper, player.getId(), updateColumn, updateValue);
                }

                toastMessage = players.get(winner).getName() + " wins!";
            }

            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}