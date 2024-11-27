package com.lta.airlock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Model.Producto;

public class CreateAccount extends AppCompatActivity implements ProductosCtrl.ProductFetchListener {

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
            // goHome();
            Log.i("airlock_555", productosCtrl.createUser(
            this,
                txtNombre.getText().toString(),
                txtApellidos.getText().toString(),
                txtTlf.getText().toString(),
                txtCorreo.getText().toString(),
                txtPassword.getText().toString()
            ));
        });

    }

    private void goHome() {
        Intent it = new Intent(this, Home.class);
        startActivity(it);
    }

    @Override
    public void onProductsFetched(List<Producto> productos) {

    }
}