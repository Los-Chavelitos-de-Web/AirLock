package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lta.airlock.R;

import java.util.ArrayList;
import java.util.List;

import Controllers.MySQL.ProductosCtrl;
import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.ProdCart;
import Model.Producto;
import RV_RelojCartItem.ProdCart_Adapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrCasual#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrCasual extends Fragment implements ProductosCtrl.ProductFetchListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<Producto> productos;
    private Button btnSearch;
    private EditText txtSearch;
    ProductosCtrl prods;

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

        btnSearch.setOnClickListener(view -> {
            prods.searchProduct(txtSearch.getText().toString(), this);
        });

        return v;
    }

    @Override
    public void onProductsFetched(List<Producto> p) {
        this.productos = p;
    }
}