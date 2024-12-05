package com.lta.airlock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Controllers.MySQL.ProductosCtrl;

public class VerifyCode extends AppCompatActivity {

    ProductosCtrl productosCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productosCtrl = new ProductosCtrl(this);
        String correo = getIntent().getStringExtra("correo");

        productosCtrl.isValidateEmail(new ProductosCtrl.CreateUserFetchListener() {
            @Override
            public void onResponse(int status, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Log.i("airlock_555", "Respuesta del servidor: " + message);
                if (status == 200) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), Home.class);
                    startActivity(it);
                } else if (status == 400) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("airlock_555", "Error en la solicitud: " + error);
                Toast.makeText(getApplicationContext(), "Intente más tarde", Toast.LENGTH_SHORT).show();
            }
        }, correo);

        setContentView(R.layout.activity_verify_code);

    }
}