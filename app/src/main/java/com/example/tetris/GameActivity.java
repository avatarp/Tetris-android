package com.example.tetris;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.lang.String.valueOf;


public class GameActivity extends AppCompatActivity {

    final Engine game = new Engine();

    final Runnable updateUI = new Runnable() {

        @Override
        public void run() {
            final TextView scoreValue = findViewById(R.id.scoreValueText);
            final View gameView = findViewById(R.id.GameSideView);
            final View nextBlockView = findViewById(R.id.nextBlockView);

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
            blackPaint.setStrokeWidth(5);

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
            String text = valueOf(game.score);
            scoreValue.setText(text);

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


        new Thread(new Runnable() {
            @Override
            public void run() {
                game.runLogic(GameActivity.this);
            }
        }).start();


        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.moveRight();
                if (game.isColliding()) {
                    game.moveLeft();
                }
                runOnUiThread(updateUI);
            }

        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game.moveLeft();
                if (game.isColliding()) {
                    game.moveRight();
                }
                runOnUiThread(updateUI);
            }

        });

        rotateLeftKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.currBlock.rotateLeft();
                if (game.isColliding()) {
                    game.currBlock.rotateRight();
                }
                runOnUiThread(updateUI);
            }

        });

        rotateRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.currBlock.rotateRight();
                if (game.isColliding()) {
                    game.currBlock.rotateLeft();
                }
                runOnUiThread(updateUI);

            }

        });

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.dropDown();
                runOnUiThread(updateUI);
            }


        });

    }

    public void triggerUIupdate() {
        runOnUiThread(updateUI);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


}




