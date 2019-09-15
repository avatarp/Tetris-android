package com.example.tetris;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
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

        /*TextView highscoresView = findViewById(R.id.TopScoresText);
        TextView timestampsTextView = findViewById(R.id.TimestampsText);
        highscoresView.setText(databaseHandler.getHighscores());
        timestampsTextView.setText(databaseHandler.getHighscoresTimestamps());*/
        ProgressBar progressBar = findViewById(R.id.progressBar);
        int bestScore = databaseHandler.getBestScore();
        int progressValue = score / bestScore;

        db.close();
        TextView pointsLeft = findViewById(R.id.onlyXPointsText);
        if (progressValue > 100) {
            progressValue = 100;
            pointsLeft.setText("");
        } else {
            if (progressValue < 1) {
                progressValue = 1;
            }
            int pointsToHighscore = bestScore - score;
            String temp = getResources().getString(R.string.onlyXPointsPrefix) + " " + pointsToHighscore + " " + getResources().getString(R.string.onlyXPointsPostfix);
            pointsLeft.setText(temp);

        }
        ObjectAnimator.ofInt(progressBar, "progress", progressValue)
                .setDuration(2000)
                .start();

        Button statsButton = findViewById(R.id.stats);
        Button menuButton = findViewById(R.id.mainMenu);
        Button replayButton = findViewById(R.id.replay);

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, stats.class);
                startActivity(intent);
                finish();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        replayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


}

