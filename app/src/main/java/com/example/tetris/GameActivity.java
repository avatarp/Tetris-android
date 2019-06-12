package com.example.tetris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ImageButton leftArrow = (ImageButton) findViewById(R.id.leftArrowButton);
        ImageButton rightArrow = (ImageButton) findViewById(R.id.rightArrowButton);
        final Engine game=new Engine();

        new Thread(new Runnable() {
            @Override
            public void run() {
                game.run();
            }
        }).start();


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                game.moveLeft();
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
        }



    }




