package com.example.cadastrologin.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.cadastrologin.activity.Cadastro2;
import com.example.cadastrologin.helper.DBhelper;
import com.example.cadastrologin.model.User;

public class UserDAO {
    private User user;
    private DBhelper db;

    public UserDAO(Context ctx, User user) {
        this.user = user;
        this.db = new DBhelper(ctx);
    }

    public void signUp(){
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        //String sql = "INSERT INTO user (mail, username, password) values ('?', '?', '?');";
        //Cursor cursor = dbLite.rawQuery(sql, new String[]{user.getMail(), user.getUsername(), user.getPassword()});

        ContentValues content = new ContentValues();
        content.put("mail", user.getMail());
        content.put("username", user.getUsername());
        content.put("password", user.getPassword());
        //Defino a "tabela", oq substituirá se houver valor nulo, e os valores
        long id = dbLite.insert("user", null, content);

        //dbLite.execSQL(sql);
    }

    public boolean signUpVality(){
        SQLiteDatabase db = this.db.getReadableDatabase();

        //Especifico o que quero que retorne.
        String[] projection = {
                "mail",
                "username",
                "password"
        };

        // Define o filtro
        // Onde WHERE selection = selectionArgs
        String selection = "mail = ?";
        String[] selectionArgs = {user.getMail()};

        // Pelo que e como eu ordeno os valores
        String sortOrder =
                "username DESC";

        //Executo o query
        Cursor cursor = db.query(
                "user",            // A tabela
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        // Caso haja algum, retorno true
        if(cursor.getCount()>0){
            return true;
        }

        return false;
    }
}
