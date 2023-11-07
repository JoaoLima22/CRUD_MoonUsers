package com.example.cadastrologin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.cadastrologin.R;
import com.example.cadastrologin.dao.UserDAO;
import com.example.cadastrologin.model.User;

public class List extends AppCompatActivity {
ListView lvUser;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        lvUser = findViewById(R.id.lvUsers);
        UserDAO uDao = new UserDAO(getApplicationContext(), new User());

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.layout_users,
                uDao.listUsers(),
                new String[]{"_id", "username"},
                new int[]{R.id.tvMailList, R.id.tvUsernameList},
                0);

        lvUser.setAdapter(adapter);

    }
}