package com.example.cadastrologin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button btn_google;

    Button btnLogin;
    EditText txtmail, txtpass;
    TextView tvSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_google = findViewById(R.id.btn_google);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        // -----------------------------------

        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
        String auxMail = sp.getString("mail", "");
        if(!auxMail.equals("")){
            Intent it = new Intent(Login.this, Main.class);
            startActivity(it);
        }

        btnLogin = findViewById(R.id.btnLogin);
        txtmail = findViewById(R.id.edtMailLogin);
        txtpass = findViewById(R.id.edtPasswordLogin);
        tvSign = findViewById(R.id.tvSign);

        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, SignUp.class);
                startActivity(it);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail, pass;
                mail = txtmail.getText().toString();
                pass = txtpass.getText().toString();

                if(mail.equals("")){txtmail.setError("Fill this field!");}
                if(pass.equals("")){txtpass.setError("Fill this field!");}

                if (mail.equals("") || pass.equals("")){
                    //Testo campos vazios
                    Toast.makeText(Login.this, "Fill both fields!", Toast.LENGTH_SHORT).show();
                } else{
                    User user = new User();
                    user.setMail(mail);
                    user.setPassword(pass);

                    UserDAO uDAO = new UserDAO(getApplicationContext(), user);
                    if(uDAO.signUpVality()==false){
                        txtmail.setText("");
                        txtmail.setError("Invalid data!");
                        txtpass.setText("");
                        txtpass.setError("Invalid data!");
                    } else{
                        User auxUser = uDAO.getUserByMail();

                        if(!auxUser.getPassword().equals(user.getPassword())){
                            Toast.makeText(Login.this, "Invalid password!", Toast.LENGTH_SHORT).show();
                            txtpass.setText("");
                            txtpass.setError("Invalid password!");
                        } else{
                            // Login permitido
                            SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("mail", user.getMail());
                            editor.commit();
                            Intent it = new Intent(Login.this, Main.class);
                            startActivity(it);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item1){
            Intent it = new Intent(this, List.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(Login.this, Main.class);
        startActivity(intent);

    }
}