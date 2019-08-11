package com.example.tetris;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_game_over);
        Bundle bundle = getIntent().getExtras();

        Integer score = bundle.getInt("newHighScore");

        TextView ScoreValueTextView = findViewById(R.id.ScoreValue);
        ScoreValueTextView.setText(score.toString());

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.SCORE, score);
        long result = db.insert(DatabaseHandler.HIGHSCORES_TABLE, null, contentValues);


        if (result == -1) {
            Toast toast = Toast.makeText(GameOverActivity.this, "Dodanie rekordu nie powiodło się.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(GameOverActivity.this, "Dodanie rekordu powiodło się!", Toast.LENGTH_LONG);
            toast.show();
        }
        TextView highscoresView = findViewById(R.id.TopScoresText);
        highscoresView.setText(databaseHandler.getHighscores());


    }


}

