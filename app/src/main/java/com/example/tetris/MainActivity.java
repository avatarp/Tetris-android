package com.example.tetris;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    boolean vibrating=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        Button controlsButton = (Button) findViewById(R.id.controlsButton);
        Button exitButton = (Button) findViewById(R.id.exitButton);

        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        controlsButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (vibrating == false)
                {
                    vibrating=true ;

                    vibe.vibrate(100);
                    vibrating=false;
                }
                return false;
            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                System.exit(0);
            }
        });

        controlsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                vibe.vibrate(100);
                Intent intent=new Intent(MainActivity.this, Controls.class);
                startActivity(intent);
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }


}
