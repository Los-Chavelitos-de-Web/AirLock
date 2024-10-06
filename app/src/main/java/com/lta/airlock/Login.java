package com.lta.airlock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    TextView lblOlvidastePssw;
    TextView lblCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnInitSesion);
        lblCreateAccount = findViewById(R.id.lblCreateAccount);
        lblOlvidastePssw = findViewById(R.id.lblOlvidastePssw);

        lblOlvidastePssw.setOnClickListener(v -> {
            msj("No avaliable");
        });

        lblCreateAccount.setOnClickListener(v -> {
            goCreateAccount();
        });

        btnLogin.setOnClickListener(v -> {
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