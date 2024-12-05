package com.lta.airlock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Controllers.MySQL.ProductosCtrl;

public class Login extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    TextView lblOlvidastePssw;
    TextView lblCreateAccount;
    TextView lblInvited;
    ProductosCtrl productosCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnInitSesion);
        lblCreateAccount = findViewById(R.id.lblCreateAccount);
        lblOlvidastePssw = findViewById(R.id.lblOlvidastePssw);
        lblInvited = findViewById(R.id.lblInvited);
        productosCtrl = new ProductosCtrl(this);

        lblOlvidastePssw.setOnClickListener(v -> {
            msj("No avaliable");
        });

        lblCreateAccount.setOnClickListener(v -> {
            goCreateAccount();
        });

        btnLogin.setOnClickListener(v -> {

            String correo = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            productosCtrl.loginUser(new ProductosCtrl.CreateUserFetchListener() {
                @Override
                public void onResponse(int status, String message) {
                    Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                    Log.i("airlock_555", "Respuesta del servidor: " + message);
                    if (status == 200) {
                        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                    } else if (status == 404) {
                        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                        Log.e("airlock_555", message);
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("airlock_555", "Error en la solicitud: " + error);
                    Toast.makeText(v.getContext(), "Intente más tarde", Toast.LENGTH_SHORT).show();
                }
            }, correo, password);
        });

        lblInvited.setOnClickListener(v -> {
            goHome();
        });

    }

    private void msj(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void goCreateAccount() {
        Intent it = new Intent(this, CreateAccount.class);
        startActivity(it);
    }

    private void goHome() {
        Intent it = new Intent(this, Home.class);
        startActivity(it);
    }
}