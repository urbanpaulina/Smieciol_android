package com.example.smiecioltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Start extends AppCompatActivity {
    private static int Time_Out = 3700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent homeIntent = new Intent(Start.this, Login.class);
                startActivity(homeIntent);
                finish();
            }
        }, Time_Out);
    }
}