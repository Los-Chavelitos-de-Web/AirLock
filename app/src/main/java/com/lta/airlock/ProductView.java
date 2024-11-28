package com.lta.airlock;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.Producto;
import RV_RelojItem.Reloj_Adapter;

public class ProductView extends AppCompatActivity implements ProductosCtrl.ProductFetchListener, Reloj_Adapter.OnItemClickListener {

    int productId;
    Producto producto;
    ImageView img;
    TextView txtNombre;
    TextView txtDescripcion;
    TextView txtCantidad;
    TextView txtPrice;
    Button btnBack;
    Button btnAddToCart;
    ImageView ivProdView;
    CartCtrl cartCtrl;
    DBHelper dbHelper;
    List<Producto> similaryProds;
    Reloj_Adapter adapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        txtNombre = findViewById(R.id.txtNombreView);
        txtDescripcion = findViewById(R.id.txtDescripcionView);
        txtCantidad = findViewById(R.id.txtCantView);
        txtPrice = findViewById(R.id.txtPriceView);
        btnBack = findViewById(R.id.btnBackView);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        rv = findViewById(R.id.listSimilariesProducts);
        ivProdView = findViewById(R.id.ivProdView);

        loadData();
        dbHelper = new DBHelper(this);
        cartCtrl = new CartCtrl(dbHelper);
        setData();

        ProductosCtrl prods = new ProductosCtrl(this);
        prods.getProductsSimilary(this, producto.getGen(), producto.getMarca());
        adapter = new Reloj_Adapter(getApplicationContext(), new ArrayList<>(), this);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(adapter);

        btnAddToCart.setOnClickListener(v -> {
            try {

                cartCtrl.addNewProduct(
                        producto.getProductoID(),
                        producto.getImg(),
                        producto.getNombre(),
                        producto.getPrecioCompra()
                );
                Toast.makeText(v.getContext(), "¡Producto añadido al carrito!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("airlock_555", e.getMessage());
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void loadData() {
        productId = getIntent().getIntExtra("product_id", -1);
        producto = new Producto(
            productId,
            getIntent().getStringExtra("nombre"),
            getIntent().getStringExtra("descripcion"),
            getIntent().getDoubleExtra("precio", 0),
            getIntent().getIntExtra("cant", 0),
            getIntent().getStringExtra("gen"),
            getIntent().getStringExtra("marca"),
            getIntent().getStringExtra("img")
        );
    }

    private void setData() {
        txtNombre.setText(producto.getNombre());
        txtDescripcion.setText(producto.getDescripcion());
        txtCantidad.setText(String.format("Cantidad: %s", producto.getStock()));
        txtPrice.setText(String.format("S/. %s", producto.getPrecioCompra()));

        Glide.with(getApplicationContext())
                .load(producto.getImg())
                .into(ivProdView);
    }

    @Override
    public void onProductsFetched(List<Producto> p) {
        similaryProds = p;

        if (similaryProds != null && !similaryProds.isEmpty()) {
            adapter.updateData(similaryProds);

        } else {
            Log.e("airlock_555", "No products found.");
        }
    }

    @Override
    public void onItemClick(Producto producto) {
        Intent it = new Intent(getApplicationContext(), ProductView.class);
        it.putExtra("product_id", producto.getProductoID());
        it.putExtra("nombre", producto.getNombre());
        it.putExtra("descripcion", producto.getDescripcion());
        it.putExtra("precio", producto.getPrecioCompra());
        it.putExtra("cant", producto.getStock());
        it.putExtra("gen", producto.getGen());
        it.putExtra("marca", producto.getMarca());
        it.putExtra("img", producto.getImg());
        startActivity(it);
    }
}