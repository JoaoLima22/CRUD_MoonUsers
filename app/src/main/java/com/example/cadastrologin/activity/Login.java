package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText txtmail, txtpass;
    TextView tvSign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtmail = findViewById(R.id.edtEmailLogin);
        txtpass = findViewById(R.id.edtPasswordLogin);
        tvSign = findViewById(R.id.tvSign);

        tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Login.this, Cadastro.class);
                startActivity(it);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail, pass;
                mail = txtmail.getText().toString();
                pass = txtpass.getText().toString();

                if(mail.equals("")){txtmail.setError("Preencha este campo!");}
                if(pass.equals("")){txtpass.setError("Preencha este campo!");}

                if (mail.equals("") || pass.equals("")){
                    //Testo campos vazios
                    Toast.makeText(Login.this, "Preencha ambos os campos!", Toast.LENGTH_SHORT).show();
                } else{
                    User user = new User();
                    user.setMail(mail);
                    user.setPassword(pass);

                    UserDAO uDAO = new UserDAO(getApplicationContext(), user);
                    if(uDAO.signUpVality()==false){
                        txtmail.setText("");
                        txtmail.setError("Dados inválidos!");
                        txtpass.setText("");
                        txtpass.setError("Dados inválidos!");
                    } else{
                        User auxUser = uDAO.getUserByMail();

                        if(!auxUser.getPassword().equals(user.getPassword())){
                            Toast.makeText(Login.this, "Senha inválida!", Toast.LENGTH_SHORT).show();
                            txtpass.setText("");
                            txtpass.setError("Senha Inválida");
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
}