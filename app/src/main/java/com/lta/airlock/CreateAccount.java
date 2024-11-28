package com.lta.airlock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Model.Producto;

public class CreateAccount extends AppCompatActivity {

    Button btnRegister;
    ProductosCtrl productosCtrl;
    EditText txtNombre;
    EditText txtApellidos;
    EditText txtTlf;
    EditText txtCorreo;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btnRegister = findViewById(R.id.btnRegister);
        productosCtrl = new ProductosCtrl(this);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTlf = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword2);

        btnRegister.setOnClickListener(v -> {
            // Pasamos un listener para manejar la respuesta
            productosCtrl.createUser(new ProductosCtrl.CreateUserFetchListener() {
                 @Override
                 public void onResponse(int status, String message) {
                     Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                     Log.i("airlock_555", "Respuesta del servidor: " + message);
                     if (status == 200) {
                         finish();
                     } else {
                         Toast.makeText(v.getContext(), "Intente más tarde", Toast.LENGTH_SHORT).show();
                         Log.e("airlock_555", message);
                     }
                 }

                 @Override
                 public void onError(String error) {
                     Log.e("airlock_555", "Error en la solicitud: " + error);
                     Toast.makeText(v.getContext(), "Intente más tarde", Toast.LENGTH_SHORT).show();
                 }
             },
            txtNombre.getText().toString(),
            txtApellidos.getText().toString(),
            txtTlf.getText().toString(),
            txtCorreo.getText().toString(),
            txtPassword.getText().toString());
        });

    }
}