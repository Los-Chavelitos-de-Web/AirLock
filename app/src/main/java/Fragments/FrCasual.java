package Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.lta.airlock.ProductView;
import com.lta.airlock.R;

import java.util.ArrayList;
import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Model.Producto;
import RV_RelojItem.Reloj_Adapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrCasual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrCasual extends Fragment implements ProductosCtrl.ProductFetchListener, Reloj_Adapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Producto> productos;
    private Button btnSearch;
    private EditText txtSearch;
    ProductosCtrl prods;

    private Reloj_Adapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FrCasual() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrCasual.
     */
    // TODO: Rename and change types and number of parameters
    public static FrCasual newInstance(String param1, String param2) {
        FrCasual fragment = new FrCasual();
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

        prods = new ProductosCtrl(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fr_casual, container, false);

        btnSearch = v.findViewById(R.id.btnSearch);
        txtSearch = v.findViewById(R.id.txtSearch);
        RecyclerView rv = v.findViewById(R.id.rvResultProds);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(v.getContext());
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);

        adapter = new Reloj_Adapter(v.getContext(), new ArrayList<>(), this);

        rv.setLayoutManager(flexboxLayoutManager);
        rv.setAdapter(adapter);

        btnSearch.setOnClickListener(view -> {
            prods.searchProduct(txtSearch.getText().toString(), this, view.getContext());
        });

        return v;
    }

    @Override
    public void onProductsFetched(List<Producto> p) {
        this.productos = p;

        if (productos != null && !productos.isEmpty()) {
            adapter.updateData(productos);
        } else {
            Log.e("airlock_555", "No products found.");
        }
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