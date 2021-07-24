package com.jonahmiddleton.players;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private int selectedPlayer;
    private DBHelper dbHelper;
    private SharedPreferences saveState;

    ListView selectList;
    TextView pSelectLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        dbHelper = new DBHelper(this);
        saveState = getSharedPreferences(MainActivity.PREFERENCES, Context.MODE_PRIVATE);

        pSelectLabel = (TextView)findViewById(R.id.pSelectLabel);
        selectList = (ListView)findViewById(R.id.selectList);

        Intent intent = getIntent();
        selectedPlayer = Integer.parseInt(intent.getStringExtra(MainActivity.MESSAGE));
        pSelectLabel.setText("Select Player " + selectedPlayer);

        ArrayList<Player> players = new ArrayList<Player>(PlayerData.getPlayers(dbHelper, PlayerContract.PlayerEntry.COLUMN_NAME_WINS, true));
        ArrayAdapter<Player> playersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, players);
        selectList.setAdapter(playersAdapter);
        selectList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Editor editor = saveState.edit();

        Player clickedPlayer = (Player)parent.getItemAtPosition(position);
        editor.putInt(MainActivity.PREF_KEY_PLAYER + selectedPlayer, clickedPlayer.getId());
        editor.commit();
        String toastMessage = clickedPlayer.getName() + " selected as player " + selectedPlayer;
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}