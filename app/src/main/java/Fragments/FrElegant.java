package Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lta.airlock.R;

import java.io.IOException;
import java.util.List;
import Controllers.ProductoCtrl;
import Model.DBHelper;
import Model.Producto;
import RV_RelojItem.Reloj_Adapter;

public class FrElegant extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private DBHelper dbHelper;
    private ProductoCtrl productoCtrl;

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

        // Inicializar DBHelper y ProductoCtrl aquí
        dbHelper = new DBHelper(getContext());
        try {
            dbHelper.createDatabase();
            productoCtrl = new ProductoCtrl(dbHelper);
        } catch (IOException e) {
            Log.e("airlock_555", "Error al crear la base de datos", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fr_elegant, container, false);
        RecyclerView rv1 = view.findViewById(R.id.rvRelojHan);
        RecyclerView rv2 = view.findViewById(R.id.rvRelojCur);

        // Obtener productos
        List<Producto> items = productoCtrl.obtenerProductos();

        // Configurar RecyclerViews
        rv1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv1.setAdapter(new Reloj_Adapter(getContext(), items));

        rv2.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv2.setAdapter(new Reloj_Adapter(getContext(), items));

        return view;
    }
}