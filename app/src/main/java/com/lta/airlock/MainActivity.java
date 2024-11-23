package com.lta.airlock;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Model.DBHelper;
import Model.Producto;

public class MainActivity extends AppCompatActivity{

    ImageButton btnLogin;
    DBHelper dbHelper;
    SQLiteDatabase db;
    private List<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conDatabase();

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            goNext();
        });

    }

    private void conDatabase() {
        try {
            dbHelper = new DBHelper(this);
            dbHelper.createDatabase(); // SQLite

            db = dbHelper.openDatabase(); // SQLite
            if (db != null) {
                Log.i("airlock_555", "CONNECTED");
            } else {
                Log.e("airlock_555", "DISCONNECT");
                Toast.makeText(this, "DISCONNECT", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.e("airlock_555", "Error al crear la base de datos", e);
        }
    }

    private void goNext() {
        Intent it = new Intent(this, Login.class);
        startActivity(it);
    }
}