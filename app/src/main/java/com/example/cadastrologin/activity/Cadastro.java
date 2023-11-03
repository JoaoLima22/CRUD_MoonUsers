package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;

public class Cadastro extends AppCompatActivity {
    Button btnSign;
    EditText txtmail, txtuser, txtpass, txtpassCon;
    TextView tvlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        btnSign = findViewById(R.id.btnSign);
        txtuser = findViewById(R.id.edtUsername);
        txtmail = findViewById(R.id.edtEmail);
        txtpass = findViewById(R.id.edtPassword);
        txtpassCon = findViewById(R.id.edtPasswordConfirm);
        tvlogin = findViewById(R.id.tvLogin);

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Cadastro.this, Login.class);
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

                if(user.equals("")){txtuser.setError("Preencha este campo!");}
                if(mail.equals("")){txtmail.setError("Preencha este campo!");}
                if(pass.equals("")){txtpass.setError("Preencha este campo!");}
                if(passCon.equals("")){txtpassCon.setError("Preencha este campo!");}

                if (user.equals("") || mail.equals("") || pass.equals("") || passCon.equals("")){
                    //Testo campos vazios
                    Toast.makeText(Cadastro.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else if(!pass.equals(passCon)){
                    //Testo senhas diferentes
                    txtpass.setError("As senhas estão diferentes!");
                    txtpassCon.setError("As senhas estão diferentes!");
                    txtpass.setText("");
                    txtpassCon.setText("");
                } else {
                    //Salvo os dados
                    UserDAO uDAO = new UserDAO(getApplicationContext(), new User(user, mail, pass));
                    if(uDAO.signUpVality()==true){
                        //Verifico se já possui cadastro com o email
                        Toast.makeText(Cadastro.this, "Email já usado em outra conta!", Toast.LENGTH_SHORT).show();
                        txtmail.setError("Email já usado em outra conta!");
                        txtmail.setText("");
                    } else{
                        //Se não houver eu salvo
                        uDAO.signUp();
                        //Limpo os campos
                        txtuser.setText("");
                        txtmail.setText("");
                        txtpass.setText("");
                        txtpassCon.setText("");
                        Toast.makeText(Cadastro.this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}
