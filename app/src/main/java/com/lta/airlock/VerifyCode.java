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

public class VerifyCode extends AppCompatActivity {

    ProductosCtrl productosCtrl;
    TextView txtCorreo;
    String correo;
    Button btnValidateCode;
    EditText txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productosCtrl = new ProductosCtrl(this);
        correo = getIntent().getStringExtra("correo");

        productosCtrl.isValidateEmail(new ProductosCtrl.CreateUserFetchListener() {
            @Override
            public void onResponse(int status, String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Log.i("airlock_555", "Respuesta del servidor: " + message);
                if (status == 200) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), Home.class);
                    if (correo != null) {
                        it.putExtra("correo", correo);
                    } else {
                        it.putExtra("correo", "");
                    }
                    startActivity(it);
                } else if (status == 400) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("airlock_555", "Error en la solicitud: " + error);
                Toast.makeText(getApplicationContext(), "Correo sin verificar", Toast.LENGTH_SHORT).show();
                initLayout();
            }
        }, correo);

    }

    private void initLayout() {
        setContentView(R.layout.activity_verify_code);

        txtCorreo = findViewById(R.id.txtCorreo);
        btnValidateCode = findViewById(R.id.btnValidateCode);
        txtCode = findViewById(R.id.txtCode);

        btnValidateCode.setOnClickListener(v -> {

            String code = txtCode.getText().toString();

            productosCtrl.validateCode(new ProductosCtrl.CreateUserFetchListener() {
                @Override
                public void onResponse(int status, String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Log.i("airlock_555", "Respuesta del servidor: " + message);

                    if (status == 200) {
                        Intent it = new Intent(v.getContext(), Home.class);
                        Log.i("airlock_555", "Código validado exitosamente");
                        it.putExtra("correo", correo);
                        // Guardar data en db
                        startActivity(it);

                        productosCtrl.deleteCode(new ProductosCtrl.CreateUserFetchListener() {
                            @Override
                            public void onResponse(int status, String message) {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                Log.i("airlock_555", "Respuesta del servidor: " + message);
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("airlock_555", "Error en la solicitud: " + error);
                                Toast.makeText(getApplicationContext(), "Error en la solicitud", Toast.LENGTH_SHORT).show();
                            }
                        }, correo, code);
                    } else if (status == 201) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error en la validación del código: " + message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String error) {
                    Log.e("airlock_555", "Error en la solicitud: " + error);
                    Toast.makeText(getApplicationContext(), "Error en la validación del código. Intenta nuevamente.", Toast.LENGTH_SHORT).show();
                    initLayout();
                }
            }, correo, code);
        });


        txtCorreo.setText(correo);
    }
}