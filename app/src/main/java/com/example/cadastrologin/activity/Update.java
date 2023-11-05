package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;

public class Update extends AppCompatActivity {
    EditText txtUsername, txtPass, txtPassCon, txtMail;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
        String auxMail = sp.getString("mail", "");

        if(auxMail.equals("")){
            Intent it = new Intent(Update.this, Login.class);
            startActivity(it);
        } else {

            User user = new User();
            user.setMail(auxMail);
            UserDAO uDAO = new UserDAO(getApplicationContext(), user);
            user = uDAO.getUserByMail();

            txtMail = findViewById(R.id.edtMailUpdate);
            txtUsername = findViewById(R.id.edtUsernameUpdate);
            txtPass = findViewById(R.id.edtPasswordUpdate);
            txtPassCon = findViewById(R.id.edtPasswordConfirmUpdate);
            btnUpdate = findViewById(R.id.btnUpdate);

            txtMail.setText(user.getMail());
            txtUsername.setText(user.getUsername());
            txtPass.setText(user.getPassword());
            txtPassCon.setText(user.getPassword());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user, mail, pass, passCon;
                    user = txtUsername.getText().toString();
                    mail = txtMail.getText().toString();
                    pass = txtPass.getText().toString();
                    passCon = txtPassCon.getText().toString();

                    if (user.equals("")){txtUsername.setError("Fill this field!");}
                    if (mail.equals("")){txtMail.setError("Fill this field!");}
                    if (pass.equals("")){txtPass.setError("Fill this field!");}
                    if (passCon.equals("")){txtPassCon.setError("Fill this field!");}

                    if (user.equals("") || mail.equals("") || pass.equals("") || passCon.equals("")) {
                        //Testo campos vazios
                        Toast.makeText(Update.this, "Fill all the fields!", Toast.LENGTH_SHORT).show();
                    } else if (!pass.equals(passCon)) {
                        txtPass.setError("Passwords are different!");
                        txtPassCon.setError("Passwords are different!");
                        txtPass.setText("");
                        txtPassCon.setText("");
                        Toast.makeText(Update.this, "Passwords are different!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Salvo os dados
                        UserDAO uDAOUpdate = new UserDAO(getApplicationContext(), new User(user, mail, pass));
                        if (uDAOUpdate.signUpVality() == true && !mail.equals(auxMail)) {
                            //Verifico se já possui cadastro com o email
                            Toast.makeText(Update.this, "Email already in used!", Toast.LENGTH_SHORT).show();
                            txtMail.setError("Email already in used!");
                            txtMail.setText("");
                        } else {
                            //Se não houver eu altero
                            if (uDAOUpdate.update(auxMail) == true) {
                                Toast.makeText(Update.this, "Successfully changed data!", Toast.LENGTH_SHORT).show();

                                SharedPreferences sp = getSharedPreferences("MoonUsers", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("mail", mail);
                                editor.commit();

                                Intent it = new Intent(Update.this, Main.class);
                                startActivity(it);
                            } else {
                                Toast.makeText(Update.this, "Error while changing data...", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            });
        }
    }
}