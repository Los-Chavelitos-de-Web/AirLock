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

import com.lta.airlock.ProductView;
import com.lta.airlock.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Controllers.MySQL.ProductosCtrl;
import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.Producto;
import RV_RelojItem.Reloj_Adapter;

public class FrElegant extends Fragment implements ProductosCtrl.ProductFetchListener, Reloj_Adapter.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final Set<String> GENM = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Masculino", "Unisex")));
    private static final Set<String> GENF = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("Femenino", "Unisex")));

    private String mParam1;
    private String mParam2;
    private List<Producto> productosM;
    private List<Producto> productosF;

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

        ProductosCtrl prods = new ProductosCtrl(getContext());
        prods.getAllProducts(this);
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
        this.productosM = filterElegantProducts(p, GENM);
        this.productosF = filterElegantProducts(p, GENF);

        if (productosM != null && !productosM.isEmpty()) {

            if (productosF != null && !productosF.isEmpty()) {
                adapter1.updateData(productosM);
                adapter2.updateData(productosF);
            }

        } else {
            Log.e("airlock_555", "No products found.");
        }
    }

    private List<Producto> filterElegantProducts(List<Producto> ps, Set<String> g) {
        List<Producto> filteredList = new ArrayList<>();

        for (Producto p : ps) {
            if (g.contains(p.getGen())) {
                filteredList.add(p);
            }
        }

        return filteredList;
    }

    @Override
    public void onItemClick(Producto producto) {
        Intent it = new Intent(getContext(), ProductView.class);
        it.putExtra("product_id", producto.getProductoID());
        it.putExtra("nombre", producto.getNombre());
        it.putExtra("descripcion", producto.getDescripcion());
        it.putExtra("precio", producto.getPrecioCompra());
        it.putExtra("cant", producto.getStock());
        it.putExtra("gen", producto.getGen());
        it.putExtra("marca", producto.getMarca());
        startActivity(it);
    }
}
