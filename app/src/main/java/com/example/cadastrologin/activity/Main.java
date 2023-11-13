package com.example.cadastrologin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Main extends AppCompatActivity {

    TextView mail, username;
    Button btnAlter, btnDelete, btnLogout, btn_google;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
//        String auxMail = sp.getString("mail", "");
//
//        User user = new User();
//        user.setMail(auxMail);
//        UserDAO uDAO = new UserDAO(getApplicationContext(), user);
//        user = uDAO.getUserByMail();
//
        mail = findViewById(R.id.txtMailUser);
//        mail.setText(user.getMail());
        username = findViewById(R.id.txtUsernameUser);
//        username.setText(user.getUsername());

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            username.setText(personName);
            mail.setText(personEmail);
        }



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
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Main.this, Delete.class);
                startActivity(it);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();

                Intent it = new Intent(Main.this, Login.class);
                startActivity(it);
            }
        });
    }

    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(Main.this, Login.class));
            }
        });
    }
}