package com.example.tetris;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DatabaseVersion = 1;
    public static final String HIGHSCORES_TABLE = "HIGHSCORES";
    public static final String SCORE = "SCORE";
    public static final String SYSDATE = "SYSDATE";
    private static final String DatabaseName = "tetrisDB";

    DatabaseHandler(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE HIGHSCORES(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "SCORE INTEGER, " + " SYSDATE DATETIME DEFAULT (datetime('now','localtime')) );");


        for (Integer i = 1; i <= 10; i++) {
            db.execSQL("INSERT INTO " + "HIGHSCORES" + " VALUES (null, " + i.toString() + ", datetime('now','localtime'));");
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getBestScore() {
        String score;
        int bestScore;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SCORE FROM " + HIGHSCORES_TABLE + " ORDER BY " + SCORE + " DESC LIMIT 1", null);
        c.moveToFirst();
        score = c.getString(0);//scores


        c.close();
        bestScore = Integer.parseInt(score);
        return bestScore;
    }

    public Long[] getScores() {
        String temp;
        List<Long> scores = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SCORE FROM " + HIGHSCORES_TABLE + " ORDER BY " + SYSDATE + " DESC", null);
        if (c.moveToFirst()) {
            do {
                temp = c.getString(0);//scores
                scores.add(Long.parseLong(temp));
            } while (c.moveToNext());
        }
        c.close();
        return scores.toArray(new Long[scores.size()]);

    }

    public String getHighscoresTimestamps() {
        String highscores = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + HIGHSCORES_TABLE + " ORDER BY " + SCORE + " DESC LIMIT 10", null);
        if (c.moveToFirst()) {
            do {
                highscores += c.getString(2) + "\n";//timestamps
            } while (c.moveToNext());
        }
        c.close();
        return highscores;
    }
}
