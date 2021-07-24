package com.jonahmiddleton.players;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.jonahmiddleton.players.PlayerContract.PlayerEntry;

public class PlayerData {

    public static List<Player> getPlayers(DBHelper dbHelper){
        return getPlayers(dbHelper, PlayerEntry.COLUMN_NAME_PLAYERNAME, true);
    }

    public static List<Player> getPlayers(DBHelper dbHelper, String sortBy, boolean orderDesc){
        return getPlayerData(dbHelper, null, null,
                sortBy + " " + ((orderDesc)? "DESC" : "ASC"));
    }

    public static Player getPlayer(DBHelper dbHelper, int id){
        List<Player> players = getPlayerData(dbHelper,
                PlayerEntry._ID + " = ?",
                new String[]{Integer.toString(id)},
                null
                );

        Iterator<Player> iterator = players.iterator();

        if (!iterator.hasNext()) {
            return null;
        }

        Player player = iterator.next();

        if (iterator.hasNext()) {
            throw new RuntimeException("Collection contains more than one item");
        }

        return player;
    }

    public static boolean addPlayer(DBHelper dbHelper, String name){
        ContentValues values = new ContentValues();
        values.put(PlayerEntry.COLUMN_NAME_PLAYERNAME, name);
        values.put(PlayerEntry.COLUMN_NAME_WINS, 0);
        values.put(PlayerEntry.COLUMN_NAME_LOSSES, 0);
        values.put(PlayerEntry.COLUMN_NAME_TIES, 0);

        long l = dbHelper.getDatabase().insert(PlayerEntry.TABLE_NAME, null, values);
        return l != -1;
    }

    public static boolean updatePlayer(DBHelper dbHelper, int id, String column, int value){
        ContentValues values = new ContentValues();
        values.put(column, value);

        return dbHelper.getDatabase().update(
                PlayerEntry.TABLE_NAME,
                values,
                PlayerEntry._ID + " = ?",
                new String[]{Integer.toString(id)}
        ) == 0;
    }

    private static List<Player> getPlayerData(DBHelper dbHelper, String selection, String[] selectionArgs, String sort){
        String[] projection = {
                PlayerEntry._ID,
                PlayerEntry.COLUMN_NAME_PLAYERNAME,
                PlayerEntry.COLUMN_NAME_WINS,
                PlayerEntry.COLUMN_NAME_LOSSES,
                PlayerEntry.COLUMN_NAME_TIES
        };

        Cursor cursor = dbHelper.getDatabase().query(
                PlayerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sort
        );

        List<Player> players = new ArrayList<>();
        while (cursor.moveToNext()){
            Player p = new Player(cursor.getInt(cursor.getColumnIndexOrThrow(PlayerEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_PLAYERNAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_WINS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_LOSSES)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME_TIES)));

            players.add(p);
        }

        return players;
    }

}
