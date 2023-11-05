package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;

public class Delete extends AppCompatActivity {
    TextView mail, username;
    Button btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
        String auxMail = sp.getString("mail", "");

        if(auxMail.equals("")){
            Intent it = new Intent(Delete.this, Login.class);
            startActivity(it);
        } else {
            User user = new User();
            user.setMail(auxMail);
            UserDAO uDAO = new UserDAO(getApplicationContext(), user);
            user = uDAO.getUserByMail();

            mail = findViewById(R.id.txtMailUserDelete);
            mail.setText(user.getMail());
            username = findViewById(R.id.txtUsernameUserDelete);
            username.setText(user.getUsername());
            btnDelete = findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();
                    uDAO.delete();
                    Intent it = new Intent(Delete.this, Login.class);
                    startActivity(it);
                }
            });
        }
    }
}