package com.example.tetris;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DatabaseVersion = 1;
    private static final String SCORES_TABLE = "SCORES";
    private static final String SCORE = "SCORE";
    private static final String BLOCKS = "BLOCKS";

    private static final String MOVESLEFT = "MOVESLEFT";
    private static final String MOVESRIGHT = "MOVESRIGHT";
    private static final String TURNLEFT = "TURNLEFT";
    private static final String TURNRIGHT = "TURNRIGHT";
    private static final String LINESCLEARED = "LINESCLEARED";


    private static final String SYSDATE = "SYSDATE";
    private static final String DatabaseName = "tetrisDB";

    DatabaseHandler(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
        SQLiteDatabase db = getReadableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Boolean highscoresExist = false;
        boolean statsExist = false;

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='SCORES'", null);

        db.execSQL("CREATE TABLE IF NOT EXISTS SCORES(_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "SCORE INTEGER, BLOCKS INTEGER, MOVESLEFT INTEGER, MOVESRIGHT INTEGER,TURNLEFT INTEGER, TURNRIGHT INTEGER, LINESCLEARED INTEGER, " + " SYSDATE DATETIME DEFAULT (datetime('now','localtime')) );" + "");
        c.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getBestScore() {
        String score;
        int bestScore;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SCORE FROM " + SCORES_TABLE + " ORDER BY " + SCORE + " DESC LIMIT 1", null);
        c.moveToFirst();
        score = c.getString(0);//scores

        c.close();
        db.close();
        bestScore = Integer.parseInt(score);
        return bestScore;
    }

    public Long[] getScores() {
        String temp;
        List<Long> scores = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SCORE FROM " + SCORES_TABLE + " ORDER BY " + SYSDATE + " DESC", null);
        if (c.moveToFirst()) {
            do {
                temp = c.getString(0);//scores
                scores.add(Long.parseLong(temp));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return scores.toArray(new Long[scores.size()]);

    }

    public String getStats() {
        String temp = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT BLOCKS, MOVESLEFT , MOVESRIGHT ,TURNLEFT , TURNRIGHT , LINESCLEARED  FROM SCORES ORDER BY _id", null);
        if (c.moveToFirst()) {
            do {
                temp += c.getString(0) + ", ";//scores
                temp += c.getString(1) + ", ";
                temp += c.getString(2) + ", ";
                temp += c.getString(3) + ", ";
                temp += c.getString(4) + ", ";
                temp += c.getString(5) + ".";
                temp += '\n';
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return temp;
    }

    public String[] getStatsByIndex(Integer i) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"SCORE", "BLOCKS", "MOVESLEFT", "MOVESRIGHT", "TURNLEFT", "TURNRIGHT", "LINESCLEARED", "SYSDATE"};
        String[] result = new String[8];
        Cursor c = db.query(SCORES_TABLE, columns, "_id=?", new String[]{i.toString()}, null, null, null);
        if (c.moveToFirst()) {
            do {
                result[0] = c.getString(0);//scores
                result[1] = c.getString(1);
                result[2] = c.getString(2);
                result[3] = c.getString(3);
                result[4] = c.getString(4);
                result[5] = c.getString(5);
                result[6] = c.getString(6);
                result[7] = c.getString(7);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return result;
    }

    public List<String> getHighscoresTimestamps() {
        List<String> highscores = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SYSDATE FROM " + SCORES_TABLE + " ORDER BY " + SCORE + " DESC LIMIT 10", null);
        if (c.moveToFirst()) {
            do {
                highscores.add(c.getString(0));//timestamps
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return highscores;
    }

    public boolean addScore(int score, int blocks, int movesLeft, int movesRight, int turnLeft, int turnRight, int linesCleared) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.SCORE, score);
        contentValues.put(DatabaseHandler.BLOCKS, blocks);
        contentValues.put(DatabaseHandler.MOVESLEFT, movesLeft);
        contentValues.put(DatabaseHandler.MOVESRIGHT, movesRight);
        contentValues.put(DatabaseHandler.TURNLEFT, turnLeft);
        contentValues.put(DatabaseHandler.TURNRIGHT, turnRight);
        contentValues.put(DatabaseHandler.LINESCLEARED, linesCleared);
        long result = db.insert(DatabaseHandler.SCORES_TABLE, null, contentValues);
        db.close();
        return result == 0;

    }








}
