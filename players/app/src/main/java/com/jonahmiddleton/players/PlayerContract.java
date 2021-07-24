package com.jonahmiddleton.players;

import android.provider.BaseColumns;

import java.security.PublicKey;

public final class PlayerContract {

    private PlayerContract() {}

    public static class PlayerEntry implements BaseColumns{
        public static final String TABLE_NAME = "player";
        public static final String COLUMN_NAME_PLAYERNAME = "name";
        public static final String COLUMN_NAME_WINS = "wins";
        public static final String COLUMN_NAME_LOSSES = "losses";
        public static final String COLUMN_NAME_TIES = "ties";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PlayerEntry.TABLE_NAME + " (" +
                        PlayerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        PlayerEntry.COLUMN_NAME_PLAYERNAME + " TEXT," +
                        PlayerEntry.COLUMN_NAME_WINS + " INTEGER," +
                        PlayerEntry.COLUMN_NAME_LOSSES + " INTEGER," +
                        PlayerEntry.COLUMN_NAME_TIES + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME;
    }
}
