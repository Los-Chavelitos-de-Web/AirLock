package com.lta.airlock;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Model.DBHelper;

public class MainActivity extends AppCompatActivity {

    ImageButton btnLogin;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        try {
            dbHelper.createDatabase();
        } catch (Exception e) {
            Log.e("airlock_555", "Error al crear la base de datos", e);
        }

        db = dbHelper.openDatabase();

        if (db != null) {
            Toast.makeText(this, "CONNECT", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "DISCONNECT", Toast.LENGTH_SHORT).show();
        }

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            goNext();
        });
    }

    private void goNext() {
        Intent it = new Intent(this, Login.class);
        startActivity(it);
    }
}