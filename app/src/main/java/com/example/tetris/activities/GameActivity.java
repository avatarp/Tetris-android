package com.example.tetris.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tetris.R;
import com.example.tetris.engine.Engine;
import com.example.tetris.engine.Renderer;

import static java.lang.String.valueOf;


public class GameActivity extends AppCompatActivity {
    Toast backToast;
    final Engine game = new Engine();
    private long backPressedTime = 0;

    public void triggerUIupdate() {
        runOnUiThread(updateUI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageButton leftArrow = findViewById(R.id.leftArrowButton);
        ImageButton rightArrow = findViewById(R.id.rightArrowButton);
        ImageButton downArrow = findViewById(R.id.dropButton);
        ImageButton rotateLeftKey = findViewById(R.id.turnLeftButton);
        ImageButton rotateRightKey = findViewById(R.id.turnRightButton);
        final ImageButton pauseButton = findViewById(R.id.pauseButton);

        new Thread(new Runnable() {
            @Override
            public void run() {
                game.runLogic(GameActivity.this);
            }
        }).start();

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning) {
                    game.moveRight();
                    triggerUIupdate();
                }
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning) {
                    game.moveLeft();
                    triggerUIupdate();
                }
            }
        });

        rotateLeftKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.rotateLeft();
                triggerUIupdate();
            }

        });

        rotateRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               game.rotateRight();
               triggerUIupdate();
            }

        });

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning) {
                    game.dropDown();
                    triggerUIupdate();
                }
            }
        });

        downArrow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vi.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    vi.vibrate(50);
                }

                game.dropToBottom();
                triggerUIupdate();
                return true;
            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.togglePause();
                triggerUIupdate();
            }


        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (game.isRunning) {
            game.togglePause();
            triggerUIupdate();
        }
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            String toastString = getResources().getString(R.string.toastOnExit);
            backToast = Toast.makeText(getBaseContext(), toastString, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    final Runnable updateUI = new Runnable() {

        @Override
        public void run() {

            final TextView scoreValue = findViewById(R.id.scoreValueText);
            final View gameView = findViewById(R.id.GameSideView);
            final View nextBlockView = findViewById(R.id.nextBlockView);
            final ImageButton pauseButton = findViewById(R.id.pauseButton);
            final TextView multiplierValue = findViewById(R.id.multiplierValueTextView);

            if (game.isRunning)
                pauseButton.setImageResource(R.drawable.ic_pause_white_24dp);
            else
                pauseButton.setImageResource(R.drawable.ic_play_arrow_white_24dp);

            if (gameView.getHeight() > 0 || gameView.getWidth() > 0) {
                if (gameView.getHeight() > gameView.getWidth() * 2.2) {
                    gameView.getLayoutParams().height = gameView.getWidth() * 2;
                }
            }

            Renderer gameRenderer=new Renderer(gameView.getHeight(),gameView.getWidth(),nextBlockView.getHeight(),nextBlockView.getWidth());
            Bitmap gameMap=gameRenderer.drawGame(game);
            Bitmap nextBlockMap=gameRenderer.drawNextBlock(game);
            nextBlockView.setBackground(new BitmapDrawable(getApplicationContext().getResources(), nextBlockMap));
            gameView.setBackground(new BitmapDrawable(getApplicationContext().getResources(), gameMap));

            scoreValue.setText(valueOf(game.stats.getScore()));
            multiplierValue.setText(valueOf(game.getMultiplier()));
        }


    };
}