package com.lta.airlock;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.ProdCart;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_payment);

        AssetManager assetManager = this.getAssets();
        txtTotalPay = findViewById(R.id.txtTotalPay);
        constraintLayout = findViewById(R.id.constraintLayoutPago);
        try {
            inputStream = assetManager.open("local.properties");
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        CartCtrl cartCtrl = new CartCtrl(dbHelper);
        prodsCart = cartCtrl.getAllProducts();

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(String.format("%s/", props.getProperty("BACKEND_HOST")))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api = retrofit.create(MercadoPagoApi.class);
        } catch (Exception e) {
            Log.e("airlock_555", e.getMessage());
        }

        for (ProdCart p : prodsCart) {
            totalPago += p.getPrice() * p.getCant();
        }

        constraintLayout.setOnClickListener(v -> {
                api.createPreference(prodsCart).enqueue(new Callback<CreatePreferenceResponse>() {
                    @Override
                    public void onResponse(Call<CreatePreferenceResponse> call, Response<CreatePreferenceResponse> response) {
                        if (response.isSuccessful()) {
                            String preferenceId = response.body().getPreferenceId();

                            String url = "https://www.mercadopago.com.pe/checkout/v1/redirect/?pref_id=" + preferenceId;

                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(browserIntent);
                            } catch (Exception e) {
                                Log.e("airlock_555", e.getMessage());
                            }
                        } else {
                            Log.e("airlock_555", "Error en la creación de la preferencia");
                        }
                    }

                    @Override
                    public void onFailure(Call<CreatePreferenceResponse> call, Throwable t) {
                        Log.e("airlock_555", "Error de conexión", t);
                    }
                });

        });

        txtTotalPay.setText(String.format("S/. %s", totalPago));

    }
}