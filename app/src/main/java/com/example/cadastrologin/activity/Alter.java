package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.example.cadastrologin.R;

public class Alter extends AppCompatActivity {
    EditText mail, username, pass, passCon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);

        username.findViewById(R.id.edtUsernameAlter);
        username.setText("Funcionou");
    }
}