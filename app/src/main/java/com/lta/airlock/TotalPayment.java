package com.lta.airlock;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.ProdCart;
import Model.CreatePreferenceRequest;
import RetrofitMercadoPago.CreatePreferenceResponse;
import RetrofitMercadoPago.MercadoPagoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TotalPayment extends AppCompatActivity {

    List<ProdCart> prodsCart;
    TextView txtTotalPay;
    Double totalPago = 0.0;
    ConstraintLayout constraintLayout;
    Retrofit retrofit;
    MercadoPagoApi api;
    Properties props = new Properties();
    InputStream inputStream;
    String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_payment);

        // Initialize assets and read the local.properties file
        AssetManager assetManager = this.getAssets();
        txtTotalPay = findViewById(R.id.txtTotalPay);
        constraintLayout = findViewById(R.id.constraintLayoutPago);

        try {
            inputStream = assetManager.open("local.properties");
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }

        // Initialize DBHelper and CartCtrl
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        CartCtrl cartCtrl = new CartCtrl(dbHelper);
        prodsCart = cartCtrl.getAllProducts();
        try {
            correo = getIntent().getStringExtra("correo");
        } catch (Exception e) {
            Log.e("airlock_555", e.getMessage());
        }

        // Initialize Retrofit and MercadoPago API
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(String.format("%s/", props.getProperty("BACKEND_HOST")))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(MercadoPagoApi.class);
        } catch (Exception e) {
            Log.e("airlock_555", "Error initializing Retrofit", e);
        }

        // Calculate total payment
        for (ProdCart p : prodsCart) {
            totalPago += p.getPrice() * p.getCant();
        }

        // Update the UI to show the total payment amount
        txtTotalPay.setText(String.format("S/. %s", totalPago));

        // Set up the click listener for the payment button
        constraintLayout.setOnClickListener(v -> {
            if (correo == null) {
                Toast.makeText(v.getContext(), "Not login", Toast.LENGTH_SHORT).show();
                Log.e("airlock_555", "Correo is null. Cannot proceed with payment.");
                return;
            }

            // Create preference request
            CreatePreferenceRequest request = new CreatePreferenceRequest(prodsCart, correo);

            // Make the API call asynchronously
            api.createPreference(request).enqueue(new Callback<CreatePreferenceResponse>() {
                @Override
                public void onResponse(Call<CreatePreferenceResponse> call, Response<CreatePreferenceResponse> response) {
                    if (response.isSuccessful()) {
                        // Successfully created the preference
                        String preferenceId = response.body().getPreferenceId();
                        String url = "https://www.mercadopago.com.pe/checkout/v1/redirect/?pref_id=" + preferenceId;

                        try {
                            // Open the MercadoPago checkout URL in a browser
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        } catch (Exception e) {
                            Log.e("airlock_555", "Error opening checkout URL", e);
                        }
                    } else {
                        Log.e("airlock_555", "Error creating payment preference. Response code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<CreatePreferenceResponse> call, Throwable t) {
                    Log.e("airlock_555", "Error making API call", t);
                }
            });
        });
    }
}