package com.example.tetris;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import static java.lang.String.valueOf;


public class GameActivity extends AppCompatActivity {
    Toast backToast;
    final Engine game = new Engine();
    private long backPressedTime = 0;

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

            Bitmap gameMap = Bitmap.createBitmap(gameView.getWidth(), gameView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas gameCanvas = new Canvas();

            Bitmap nextBlockMap = Bitmap.createBitmap(nextBlockView.getWidth(), nextBlockView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas nextBlockCanvas = new Canvas();

            Paint whitePaint = new Paint();
            whitePaint.setColor(Color.WHITE);
            whitePaint.setStyle(Paint.Style.STROKE);

            Paint redPaint = new Paint();
            redPaint.setColor(Color.RED);
            redPaint.setStyle(Paint.Style.FILL);

            Paint greenPaint = new Paint();
            greenPaint.setColor(Color.GREEN);
            greenPaint.setStyle(Paint.Style.FILL);

            Paint bluePaint = new Paint();
            bluePaint.setColor(Color.BLUE);
            bluePaint.setStyle(Paint.Style.FILL);

            Paint blackPaint = new Paint();
            blackPaint.setColor(Color.BLACK);
            blackPaint.setStyle(Paint.Style.STROKE);
            blackPaint.setStrokeWidth(3);

            gameCanvas.setBitmap(gameMap);
            nextBlockCanvas.setBitmap(nextBlockMap);



            float bitmapWidth = gameView.getWidth();
            float bitmapHeight = gameView.getHeight();
            float blockHeight = bitmapHeight / 20;
            float blockWidth = bitmapWidth / 10;

            Paint color = whitePaint;
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 10; j++) {
                    if (game.well.getGameGrid()[i][j] == 1) {
                        color = redPaint;
                    }
                    if (game.well.getGameGrid()[i][j] == 2) {
                        color = greenPaint;
                    }
                    if (game.well.getGameGrid()[i][j] == 3) {
                        color = bluePaint;
                    }

                    if (game.well.getGameGrid()[i][j] > 0) {
                        gameCanvas.drawRect(j * blockWidth, i * blockHeight, j * blockWidth + blockWidth, i * blockHeight + blockHeight, color);
                        gameCanvas.drawRect(j * blockWidth, i * blockHeight, j * blockWidth + blockWidth, i * blockHeight + blockHeight, blackPaint);
                    }
                }
            }

            for (int i = 0; i < 20; i++) {
                for (int j = -3; j < 10; j++) {
                    if (game.currBlock.coordX == j && game.currBlock.coordY == i) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (game.currBlock.getPosition()[l][k]) {
                                    if (game.currBlock.getColor() == 1) {
                                        color = redPaint;
                                    }
                                    if (game.currBlock.getColor() == 2) {
                                        color = greenPaint;
                                    }
                                    if (game.currBlock.getColor() == 3) {
                                        color = bluePaint;
                                    }
                                    if (game.currBlock.getColor() > 0) {
                                        gameCanvas.drawRect(j * blockWidth + k * blockWidth, i * blockHeight + l * blockHeight, j * blockWidth + blockWidth + k * blockWidth, i * blockHeight + blockHeight + l * blockHeight, color);
                                        gameCanvas.drawRect(j * blockWidth + k * blockWidth, i * blockHeight + l * blockHeight, j * blockWidth + blockWidth + k * blockWidth, i * blockHeight + blockHeight + l * blockHeight, blackPaint);
                                    }
                                }

                            }
                        }
                    }
                }
            }


            for (int k = 0; k < 4; k++) {
                for (int l = 0; l < 4; l++) {
                    if (game.nextBlock.getPosition()[l][k]) {

                        if (game.nextBlock.getColor() == 1) {
                            color = redPaint;
                        }
                        if (game.nextBlock.getColor() == 2) {
                            color = greenPaint;
                        }
                        if (game.nextBlock.getColor() == 3) {
                            color = bluePaint;
                        }
                        if (game.nextBlock.getColor() > 0) {
                            nextBlockCanvas.drawRect(k * blockWidth, l * blockHeight, blockWidth + k * blockWidth, blockHeight + l * blockHeight, color);
                            nextBlockCanvas.drawRect(k * blockWidth, l * blockHeight, blockWidth + k * blockWidth, blockHeight + l * blockHeight, blackPaint);
                        }
                    }
                }
            }
            gameCanvas.drawRect(0, 0, gameView.getWidth() - 1, gameView.getHeight() - 1, whitePaint);
            nextBlockView.setBackground(new BitmapDrawable(getApplicationContext().getResources(), nextBlockMap));
            gameView.setBackground(new BitmapDrawable(getApplicationContext().getResources(), gameMap));
            scoreValue.setText(valueOf(game.score));
            multiplierValue.setText(valueOf(game.getMultiplier()));

        }


    };

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
                    if (game.isColliding()) {
                        game.moveLeft();
                    }
                }
                triggerUIupdate();
            }

        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning) {
                    game.moveLeft();
                    if (game.isColliding()) {
                        game.moveRight();
                    }
                }
                triggerUIupdate();
            }

        });

        rotateLeftKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning) {
                    game.currBlock.rotateLeft();
                    if (game.isColliding()) {
                        game.currBlock.rotateRight();
                    } else {
                        game.countRotationLeft();
                    }
                }
                triggerUIupdate();
            }

        });

        rotateRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning) {
                    game.currBlock.rotateRight();
                    if (game.isColliding()) {
                        game.currBlock.rotateLeft();
                    } else {
                        game.countRotationRight();
                    }
                    triggerUIupdate();

                }
            }

        });

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isRunning)
                    game.dropDown();
                triggerUIupdate();
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

    public void triggerUIupdate() {
        runOnUiThread(updateUI);
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

}




