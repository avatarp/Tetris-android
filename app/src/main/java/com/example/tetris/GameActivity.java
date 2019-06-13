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
        final Engine game=new Engine();

        final Runnable updateUI =new Runnable() {

            @Override
            public void run() {
                final TextView scoreValue=(TextView) findViewById(R.id.scoreValueText);
                final View gameView=(View) findViewById(R.id.GameSideView);
                String text;
                Bitmap gameMap=Bitmap.createBitmap(gameView.getWidth(),gameView.getHeight(), Bitmap.Config.RGB_565);
                Canvas bcgrnd=new Canvas();
                Paint white=new Paint();
                bcgrnd.setBitmap(gameMap);
                white.setColor(Color.WHITE);
                white.setStrokeWidth(5);
                white.setStyle(Paint.Style.STROKE);


                float bitmapWidth=gameView.getWidth();
                float bitmapHeight=gameView.getHeight();
                float blockHeight=bitmapHeight/20;
                float blockWidth=bitmapWidth/10;
                Float e=bitmapHeight;
                Float f=bitmapWidth;
                Float g=blockHeight;
                Float h=blockWidth;

                Log.e("debug","Height:"+e.toString());
                Log.e("debug","Width:"+f.toString());
                Log.e("debug","BHeight:"+g.toString());
                Log.e("debug","BWidth:"+h.toString());
                for(int i=0;i<20;i++)
                {
                    for(int j=0;j<10;j++)
                    {
                        if(game.well.getGameGrid()[i][j]>0)
                        bcgrnd.drawRect(j*blockHeight,i*blockWidth,j*blockHeight+blockHeight,i*blockWidth+blockWidth,white);
                        if(game.currBlock.coordX==j && game.currBlock.coordY==i)
                        { for(int k=0;k<4;k++)
                            {
                                for(int l=0;l<4;l++) {
                                    if(game.currBlock.getPosition()[k][l])
                                    bcgrnd.drawRect(j * blockHeight+k* blockHeight, i * blockWidth+l* blockWidth, j * blockHeight + blockHeight+k* blockHeight, i * blockWidth + blockWidth+l* blockWidth, white);
                                }
                            }
                        }

                    }
                }


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





    }




