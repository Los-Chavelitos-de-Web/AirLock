package Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lta.airlock.ProductView;
import com.lta.airlock.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Controllers.SQLite.ProductoCtrl;
import Model.DBHelper;
import Model.Producto;
import RV_RelojItem.Reloj_Adapter;

public class FrElegant extends Fragment implements ProductosCtrl.ProductFetchListener, Reloj_Adapter.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private DBHelper dbHelper;
    private ProductoCtrl productoCtrl;
    private List<Producto> productos;  // List of products

    private Reloj_Adapter adapter1;
    private Reloj_Adapter adapter2;

    public FrElegant() {
        // Required empty public constructor
    }

    public static FrElegant newInstance(String param1, String param2) {
        FrElegant fragment = new FrElegant();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dbHelper = new DBHelper(getContext());
        try {
            dbHelper.createDatabase();
            productoCtrl = new ProductoCtrl(dbHelper);
        } catch (IOException e) {
            Log.e("airlock_555", "Error al crear la base de datos", e);
        }

        ProductosCtrl prods = new ProductosCtrl(getContext());
        prods.getProducts(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fr_elegant, container, false);

        // Find RecyclerViews by ID
        RecyclerView rv1 = view.findViewById(R.id.rvRelojHan);
        RecyclerView rv2 = view.findViewById(R.id.rvRelojCur);

        // Initialize the RecyclerView adapters with an empty list
        adapter1 = new Reloj_Adapter(getContext(), new ArrayList<>(), this);
        adapter2 = new Reloj_Adapter(getContext(), new ArrayList<>(), this);

        rv1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv1.setAdapter(adapter1);

        rv2.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv2.setAdapter(adapter2);

        return view;
    }

    @Override
    public void onProductsFetched(List<Producto> p) {
        this.productos = p;

        if (productos != null && !productos.isEmpty()) {
            adapter1.updateData(productos);
            adapter2.updateData(productos);
        } else {
            Log.e("airlock_555", "No products found.");
        }
    }

    @Override
    public void onItemClick(Producto producto) {
        Intent it = new Intent(getContext(), ProductView.class);
        it.putExtra("product_id", producto.getProductoID());
        startActivity(it);
    }
}
