package com.example.tetris.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.tetris.R;

public class ControlsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_controls);
    }

}