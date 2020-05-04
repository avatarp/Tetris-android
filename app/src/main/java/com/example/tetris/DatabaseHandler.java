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
    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {

        super(context, DatabaseName, null, DatabaseVersion);
        if (db == null || !db.isOpen()) {
            db = getReadableDatabase();
            onCreate(db);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Cursor c = db.rawQuery("SELECT name " +
                "FROM sqlite_master " +
                "WHERE type='table' " +
                "AND name='SCORES'", null);

        db.execSQL("CREATE TABLE IF NOT EXISTS SCORES(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, SCORE INTEGER, BLOCKS INTEGER, " +
                "MOVESLEFT INTEGER, MOVESRIGHT INTEGER,TURNLEFT INTEGER, TURNRIGHT INTEGER," +
                " LINESCLEARED INTEGER, " +
                "SYSDATE DATETIME DEFAULT (datetime('now','localtime')) );" + "");
        c.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getBestScore() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SCORE " +
                "FROM " + SCORES_TABLE + " " +
                "ORDER BY " + SCORE + " DESC LIMIT 1",
                null);

        c.moveToFirst();
        if (c.getString(0) == null) {
            c.close();
            db.close();
            return 0;
        }
        int bestScore = Integer.parseInt(c.getString(0));
        c.close();
        db.close();
        return bestScore;
    }

    public Long[] getScores() {
        List<Long> scores = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SCORE " +
                "FROM " + SCORES_TABLE +
                " ORDER BY " + SYSDATE + " DESC",
                null);

        if (c.moveToFirst()) {
            do {
                scores.add(Long.parseLong(c.getString(0)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return scores.toArray(new Long[scores.size()]);

    }

    public String[] getStatsByIndex(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {SCORE, BLOCKS, MOVESLEFT, MOVESRIGHT, TURNLEFT, TURNRIGHT,
                LINESCLEARED, SYSDATE};
        Cursor c = db.query(SCORES_TABLE, columns, "_id=?", new String[]{id.toString()},
                null, null, null);
        String[] result = new String[8];
        if (c.moveToFirst()) {
            do {
                for(int i = 0;i<8;i++){
                    result[i]=c.getString(i);
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return result;
    }

    public boolean addScore(GameStats stats) {

        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.SCORE, stats.getScore());
        contentValues.put(DatabaseHandler.BLOCKS, stats.getTotalBlocks());
        contentValues.put(DatabaseHandler.MOVESLEFT, stats.getMovesLeft());
        contentValues.put(DatabaseHandler.MOVESRIGHT, stats.getMovesRight());
        contentValues.put(DatabaseHandler.TURNLEFT, stats.getRotationsLeft());
        contentValues.put(DatabaseHandler.TURNRIGHT, stats.getRotationsRight());
        contentValues.put(DatabaseHandler.LINESCLEARED, stats.getLinesCleared());
        long result = db.insert(DatabaseHandler.SCORES_TABLE, null, contentValues);
        db.close();
        return result == 0;
    }

}
