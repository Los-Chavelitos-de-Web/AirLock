package com.lta.airlock;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.Producto;

public class ProductView extends AppCompatActivity implements ProductosCtrl.ProductFetchListener {

    int productId;
    Producto producto;
    ImageView img;
    TextView txtNombre;
    TextView txtDescripcion;
    TextView txtPrice;
    Button btnBack;
    Button btnAddToCart;
    CartCtrl cartCtrl;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        txtNombre = findViewById(R.id.txtNombreView);
        txtDescripcion = findViewById(R.id.txtDescripcionView);
        txtPrice = findViewById(R.id.txtPriceView);
        btnBack = findViewById(R.id.btnBackView);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        loadData();
        dbHelper = new DBHelper(this);
        cartCtrl = new CartCtrl(dbHelper);
        ProductosCtrl prods = new ProductosCtrl(this);
        prods.getProductsForId(this, productId);

        btnAddToCart.setOnClickListener(v -> {
            // Agrega el producto al carrito
            cartCtrl.addNewProduct(
                    "",
                    producto.getNombre(),
                    producto.getPrecioCompra()
            );
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void loadData() {
        productId = getIntent().getIntExtra("product_id", -1);
    }

    private void setData() {
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtPrice.setText("" + producto.getPrecioCompra());
    }

    @Override
    public void onProductsFetched(List<Producto> p) {
        this.producto = p.get(0);
        setData();
    }
}