package com.example.tetris.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tetris.DatabaseHandler;
import com.example.tetris.R;


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
        TextView topGameOverTextView = findViewById(R.id.gameOverTopText);
        TextView bottomGameOveTextView = findViewById(R.id.gameOverBottomText);

        ScoreValueTextView.setText(score.toString());

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        ProgressBar progressBar = findViewById(R.id.progressBar);

        int bestScore = databaseHandler.getBestScore();
        int progressValue = 100 * score / bestScore;


        if (progressValue > 100) { progressValue = 100; bottomGameOveTextView.setText("");
        } else if (score == bestScore) {
            topGameOverTextView.setText(getResources().getString(R.string.youDidIt));
            bottomGameOveTextView.setText(getResources().getString(R.string.newHighscore));
        } else {
            if (progressValue < 1) {
                progressValue = 1;
            }
            int pointsToHighscore = bestScore - score;
            String temp = getResources().getString(R.string.onlyXPointsPrefix) + " " + pointsToHighscore + " " + getResources().getString(R.string.onlyXPointsPostfix);
            bottomGameOveTextView.setText(temp);

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
                Intent intent = new Intent(GameOverActivity.this, statsActivity.class);
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