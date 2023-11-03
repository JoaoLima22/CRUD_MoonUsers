package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;

public class Main extends AppCompatActivity {
    TextView mail, username;
    Button btnAlter, btnDelete, btnLogout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
        String auxMail = sp.getString("mail", "");

        User user = new User();
        user.setMail(auxMail);
        UserDAO uDAO = new UserDAO(getApplicationContext(), user);
        user = uDAO.getUserByMail();

        mail = findViewById(R.id.txtMailUser);
        mail.setText(user.getMail());
        username = findViewById(R.id.txtUsernameUser);
        username.setText(user.getUsername());

        btnAlter = findViewById(R.id.btnAlter);
        btnDelete = findViewById(R.id.btnDelete);
        btnLogout = findViewById(R.id.btnLoggout);

        btnAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Main.this, Update.class);
                startActivity(it);
            }
        });
    }
}