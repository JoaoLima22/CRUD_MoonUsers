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

public class SignUp extends AppCompatActivity {
    Button btnSign;
    EditText txtmail, txtuser, txtpass, txtpassCon;
    TextView tvlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
        String auxMail = sp.getString("mail", "");

        if(!auxMail.equals("")){
            Intent it = new Intent(SignUp.this, Main.class);
            startActivity(it);
        }

        btnSign = findViewById(R.id.btnSign);
        txtuser = findViewById(R.id.edtUsername);
        txtmail = findViewById(R.id.edtMail);
        txtpass = findViewById(R.id.edtPassword);
        txtpassCon = findViewById(R.id.edtPasswordConfirm);
        tvlogin = findViewById(R.id.tvLogin);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignUp.this, Login.class);
                startActivity(it);
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, mail, pass, passCon;
                user = txtuser.getText().toString();
                mail = txtmail.getText().toString();
                pass = txtpass.getText().toString();
                passCon = txtpassCon.getText().toString();

                if(user.equals("")){txtuser.setError("Fill this field!");}
                if(mail.equals("")){txtmail.setError("Fill this field!");}
                if(pass.equals("")){txtpass.setError("Fill this field!");}
                if(passCon.equals("")){txtpassCon.setError("Fill this field!");}

                if (user.equals("") || mail.equals("") || pass.equals("") || passCon.equals("")){
                    //Testo campos vazios
                    Toast.makeText(SignUp.this, "Fill all the fields!", Toast.LENGTH_SHORT).show();
                } else if(!pass.equals(passCon)){
                    //Testo senhas diferentes
                    txtpass.setError("Passwords are different!");
                    txtpassCon.setError("Passwords are different!");
                    txtpass.setText("");
                    txtpassCon.setText("");
                    Toast.makeText(SignUp.this, "Passwords are different!", Toast.LENGTH_SHORT).show();
                } else {
                    //Salvo os dados
                    UserDAO uDAO = new UserDAO(getApplicationContext(), new User(user, mail, pass));
                    if(uDAO.signUpVality()==true){
                        //Verifico se já possui cadastro com o email
                        Toast.makeText(SignUp.this, "Email already in used!", Toast.LENGTH_SHORT).show();
                        txtmail.setError("Email already in used!");
                        txtmail.setText("");
                    } else{
                        //Se não houver eu salvo
                        uDAO.signUp();
                        //Limpo os campos
                        txtuser.setText("");
                        txtmail.setText("");
                        txtpass.setText("");
                        txtpassCon.setText("");

                        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("mail", mail);
                        editor.commit();

                        Toast.makeText(SignUp.this, "Registration completed!", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(SignUp.this, Main.class);
                        startActivity(it);
                    }
                }
            }
        });
    }
}
