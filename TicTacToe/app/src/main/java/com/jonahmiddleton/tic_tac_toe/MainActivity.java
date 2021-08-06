 package com.jonahmiddleton.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 public class MainActivity extends AppCompatActivity {

     enum PlayerSpace{
         Empty, X, O
     }

     enum WinState{
         Win, Tie, None
     }

     final String PREF_FILE = "com.jonahmiddleton.TicTacToe.prefs";
     final String MOVE_PREF = "MOVECOUNT";
     final String TURN_PREF = "TURN";
     final String STATE_PREF = "WINSTATE";
     final String BOARD_PREF = "BOARD";

     TextView messageBoxTextView;
     Button newGameButton;

     Space[][] board;
     int n = 3;
     int moveCount = 0;
     boolean isXTurn = true;
     WinState winState = WinState.None;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch(NullPointerException e) {}

        setContentView(R.layout.activity_main);

        board = new Space[n][n];

        board[0][0] = new Space((Button)findViewById(R.id.gameSpace0_0));
        board[0][0].button.setOnClickListener(new GameClickListener(0,0));

        board[0][1] = new Space((Button)findViewById(R.id.gameSpace0_1));
        board[0][1].button.setOnClickListener(new GameClickListener(0,1));

        board[0][2] = new Space((Button)findViewById(R.id.gameSpace0_2));
        board[0][2].button.setOnClickListener(new GameClickListener(0,2));

        board[1][0] = new Space((Button)findViewById(R.id.gameSpace1_0));
        board[1][0].button.setOnClickListener(new GameClickListener(1,0));

        board[1][1] = new Space((Button)findViewById(R.id.gameSpace1_1));
        board[1][1].button.setOnClickListener(new GameClickListener(1,1));

        board[1][2] = new Space((Button)findViewById(R.id.gameSpace1_2));
        board[1][2].button.setOnClickListener(new GameClickListener(1,2));

        board[2][0] = new Space((Button)findViewById(R.id.gameSpace2_0));
        board[2][0].button.setOnClickListener(new GameClickListener(2,0));

        board[2][1] = new Space((Button)findViewById(R.id.gameSpace2_1));
        board[2][1].button.setOnClickListener(new GameClickListener(2,1));

        board[2][2] = new Space((Button)findViewById(R.id.gameSpace2_2));
        board[2][2].button.setOnClickListener(new GameClickListener(2,2));

        messageBoxTextView = (TextView)findViewById(R.id.messageBoxTextView);
        loadState();
        updateMessage();

        newGameButton = (Button)findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveCount = 0;
                isXTurn = true;
                winState = WinState.None;

                for(int y = 0; y < n; y++){
                    for(int x = 0; x < n; x++){
                        board[y][x].player = PlayerSpace.Empty;
                        board[y][x].button.setText("");
                    }
                }

                updateMessage();
            }
        });
    }

    private WinState checkWin(int y, int x, PlayerSpace player){
        //Adapted from https://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over

        //check col
        for(int i = 0; i < n; i++){
            if(board[i][x].player != player)
                break;
            if(i == n-1){
                return WinState.Win;
            }
        }

        //check row
        for(int i = 0; i < n; i++){
            if(board[y][i].player != player)
                break;
            if(i == n-1){
                return WinState.Win;
            }
        }

        //check diag
        if(x == y){
            //we're on a diagonal
            for(int i = 0; i < n; i++){
                if(board[i][i].player != player)
                    break;
                if(i == n-1){
                    return WinState.Win;
                }
            }
        }

        //check anti diag
        if(x + y == n - 1){
            for(int i = 0; i < n; i++){
                if(board[i][(n-1)-i].player != player)
                    break;
                if(i == n-1){
                    return WinState.Win;
                }
            }
        }

        //check draw
        if(moveCount == Math.pow(n, 2)){
            return WinState.Tie;
        }

        return WinState.None;
    }

     private void updateMessage() {
        String message = "";

        switch (winState){
            case None:
                message = "Player " + getPlayerText(getPlayer(true)) + "'s turn";
                break;
            case Win:
                message = getPlayerText(getPlayer(false)) + " wins!";
                break;
            case Tie:
                message = "It's a tie!";
                break;
        }

         messageBoxTextView.setText(message);
     }

    private PlayerSpace getPlayer(boolean current){
        boolean xTurn = (current)? isXTurn : !isXTurn;
        return (xTurn)? PlayerSpace.X : PlayerSpace.O;
    }

    private String getPlayerText(PlayerSpace player){
        switch (player){
            case O:
                return "O";
            case X:
                return "X";
            default:
                return "";
        }
    }

    private void saveState(){
        SharedPreferences prefs = this.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(MOVE_PREF, moveCount);
        editor.putBoolean(TURN_PREF, isXTurn);
        editor.putString(STATE_PREF, winState.toString());

        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                editor.putString(BOARD_PREF + "_" + y + "_" + x, board[y][x].player.toString());
            }
        }
        editor.apply();
    }

    private void loadState(){
        SharedPreferences prefs = this.getSharedPreferences(PREF_FILE, 0);

        if(prefs.contains(MOVE_PREF)){
            moveCount = prefs.getInt(MOVE_PREF, 0);
        }

        if(prefs.contains(TURN_PREF)){
            isXTurn = prefs.getBoolean(TURN_PREF, true);
        }

        if(prefs.contains(STATE_PREF)){
            winState = WinState.valueOf(prefs.getString(STATE_PREF, WinState.None.toString()));
        }

        for(int y = 0; y < n; y++){
            for(int x = 0; x < n; x++){
                String key = BOARD_PREF + "_" + y + "_" + x;
                if(prefs.contains(key)){
                    PlayerSpace player = PlayerSpace.valueOf(prefs.getString(key, PlayerSpace.Empty.toString()));
                    board[y][x].player = player;
                    board[y][x].button.setText(getPlayerText(player));
                }

            }
        }
    }

     private class Space{
        PlayerSpace player;
        Button button;

        public Space(Button button){
            this.player = PlayerSpace.Empty;
            this.button = button;
        }
     }

     private class GameClickListener implements View.OnClickListener {
         int x;
         int y;

        public GameClickListener(int y, int x){
            this.y = y;
            this.x = x;
        }

         @Override
         public void onClick(View v) {
            if(board[y][x].player == PlayerSpace.Empty && winState == WinState.None){
                board[y][x].player = getPlayer(true);
                Button button = (Button)v;
                button.setText(getPlayerText(getPlayer(true)));

                moveCount++;
                winState = checkWin(y, x, getPlayer(true));

                isXTurn = !isXTurn;
                updateMessage();
                saveState();
            }
         }
     }
 }