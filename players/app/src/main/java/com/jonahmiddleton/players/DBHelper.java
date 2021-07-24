package com.jonahmiddleton.players;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.jonahmiddleton.players.PlayerContract.PlayerEntry;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Players.db";
    private static SQLiteDatabase db;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.db = this.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase(){
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlayerEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PlayerEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
