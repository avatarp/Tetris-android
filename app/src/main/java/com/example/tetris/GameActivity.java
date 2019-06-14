package com.example.tetris;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.lang.String.valueOf;


public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        ImageButton leftArrow = (ImageButton) findViewById(R.id.leftArrowButton);
        ImageButton rightArrow = (ImageButton) findViewById(R.id.rightArrowButton);
        ImageButton downArrow = (ImageButton) findViewById(R.id.dropButton);
        ImageButton rotateLeftKey = (ImageButton) findViewById(R.id.turnLeftButton);
        ImageButton rotateRightKey = (ImageButton) findViewById(R.id.turnRightButton);
        final Engine game=new Engine();

        final Runnable updateUI =new Runnable() {

            @Override
            public void run() {
                final TextView scoreValue=(TextView) findViewById(R.id.scoreValueText);
                final View gameView=(View) findViewById(R.id.GameSideView);
                final View nextBlockView = (View) findViewById(R.id.nextBlockView);
                String text;
                Bitmap gameMap=Bitmap.createBitmap(gameView.getWidth(),gameView.getHeight(), Bitmap.Config.RGB_565);
                Canvas gameCanvas = new Canvas();

                Bitmap nextBlockMap = Bitmap.createBitmap(nextBlockView.getWidth(), nextBlockView.getHeight(), Bitmap.Config.RGB_565);
                Canvas nextBlockCanvas = new Canvas();

                Paint white=new Paint();
                white.setColor(Color.WHITE);
                white.setStrokeWidth(5);
                white.setStyle(Paint.Style.STROKE);

                gameCanvas.setBitmap(gameMap);
                nextBlockCanvas.setBitmap(nextBlockMap);



                float bitmapWidth=gameView.getWidth();
                float bitmapHeight=gameView.getHeight();
                float blockHeight=bitmapHeight/20;
                float blockWidth=bitmapWidth/10;
                /*Float e=bitmapHeight;
                Float f=bitmapWidth;
                Float g=blockHeight;
                Float h=blockWidth;

                Log.e("debug","Height:"+e.toString());
                Log.e("debug","Width:"+f.toString());
                Log.e("debug","BHeight:"+g.toString());
                Log.e("debug","BWidth:"+h.toString());*/
                for(int i=0;i<20;i++)
                {
                    for(int j=0;j<10;j++)
                    {
                        if(game.well.getGameGrid()[i][j]>0)
                            gameCanvas.drawRect(j * blockWidth, i * blockHeight, j * blockWidth + blockWidth, i * blockHeight + blockHeight, white);
                        if(game.currBlock.coordX==j && game.currBlock.coordY==i)
                        { for(int k=0;k<4;k++)
                            {
                                for(int l=0;l<4;l++) {
                                    if (game.currBlock.getPosition()[l][k])
                                        gameCanvas.drawRect(j * blockWidth + k * blockWidth, i * blockHeight + l * blockHeight, j * blockWidth + blockWidth + k * blockWidth, i * blockHeight + blockHeight + l * blockHeight, white);
                                }
                            }
                        }

                    }
                }

                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        if (game.nextBlock.getPosition()[l][k])
                            nextBlockCanvas.drawRect(k * blockWidth, l * blockHeight, blockWidth + k * blockWidth, blockHeight + l * blockHeight, white);
                    }
                }
                nextBlockView.setBackground(new BitmapDrawable(nextBlockMap));
                gameView.setBackground(new BitmapDrawable(gameMap));
                text=valueOf(game.score);
                scoreValue.setText(text);

            }


        };


        new Thread(new Runnable() {
            @Override
            public void run() {
                game.score++;
                Integer totalBlocks=0;
                synchronized (this){
                    while (game.spawn())
                    {
                        Log.d("debug","Spawn is true");
                        while( game.dropDown())
                        {
                            Log.d("debug","dropdown is true");
                            Log.d("debug","currposY:"+ game.currBlock.coordY.toString());
                            Log.d("debug","currposX:"+ game.currBlock.coordX.toString());
                            runOnUiThread(updateUI);
                            try {
                                wait( game.waitTime);
                            } catch (InterruptedException e) {

                            }
                        }
                        Log.d("debug","add block");
                        game.score++;
                        game.well.addBlock( game.currBlock);
                        totalBlocks++;
                        Log.d("debug","Blocks:"+totalBlocks.toString());
                        game.score+= game.well.updateGrid();

                    }
                }
            }
        }).start();



        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game.moveLeft();
                runOnUiThread(updateUI);
                //GameActivity.this.notify();
            }

            });


        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.moveRight();
                runOnUiThread(updateUI);
                //GameActivity.this.notify();
            }

        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game.moveLeft();
                runOnUiThread(updateUI);
                //GameActivity.this.notify();
            }

        });

        rotateLeftKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.currBlock.rotateLeft();
                runOnUiThread(updateUI);
            }

        });

        rotateRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.currBlock.rotateRight();
                runOnUiThread(updateUI);
                //GameActivity.this.notify();
            }

        });

        downArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                game.dropDown();
                runOnUiThread(updateUI);
            }


        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



    }




