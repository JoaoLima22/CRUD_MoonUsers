package com.example.cadastrologin.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        ContentValues content = new ContentValues();
        content.put("mail", user.getMail());
        content.put("username", user.getUsername());
        content.put("password", user.getPassword());
        //Defino a "tabela", oq substituirá se houver valor nulo, e os valores
        long id = dbLite.insert("user", null, content);

        //dbLite.execSQL(sql);
    }
    public User getUserByMail(){
        SQLiteDatabase dbLite = this.db.getReadableDatabase();

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
        String sortOrder = "username DESC";

        //Executo o query
        Cursor c = dbLite.query(
                "user",            // A tabela
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        if(c != null){
            c.moveToFirst();
        }
        User user = new User();
        user.setMail(c.getString(0));
        user.setUsername(c.getString(1));
        user.setPassword(c.getString(2));

        return user;
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

    public boolean update(String mail){
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put("mail", user.getMail());
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());

        // Which row to update, based on the title
        String selection = "mail LIKE ?";
        String[] selectionArgs = {mail};

        int count = dbLite.update(
                "user",
                values,
                selection,
                selectionArgs);

        if (count > 0){
            return true;
        }
        return false;
    }
}
