package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cadastrologin.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
                String auxMail = sp.getString("mail", "");

                if(auxMail.equals("")) {
                    Intent it = new Intent(Splash.this, Login.class);
                    startActivity(it);
                } else {
                    Intent it = new Intent(Splash.this, Login.class);
                    startActivity(it);
                }
            }
        }, 3000);
    }
}