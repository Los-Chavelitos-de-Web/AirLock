package Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lta.airlock.R;

import java.util.ArrayList;
import java.util.List;

import Controllers.SQLite.CartCtrl;
import Model.DBHelper;
import Model.ProdCart;
import RV_RelojCartItem.ProdCart_Adapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrSports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrSports extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<ProdCart> prods_cart;
    private ProdCart_Adapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FrSports() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrSports.
     */
    // TODO: Rename and change types and number of parameters
    public static FrSports newInstance(String param1, String param2) {
        FrSports fragment = new FrSports();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fr_sports, container, false);

        try {DBHelper dbHelper = new DBHelper(view.getContext());
            CartCtrl cartCtrl = new CartCtrl(dbHelper);

            prods_cart = cartCtrl.getAllProducts();

            if (prods_cart == null) {
                Log.d("airlock_555", "Is null");
                prods_cart = new ArrayList<>();
            }

            if (prods_cart.isEmpty()) {
                Log.d("airlock_555", "No se encontraron productos en el carrito.");
            }

            RecyclerView rv = view.findViewById(R.id.rvResultProds);
            adapter = new ProdCart_Adapter(view.getContext(), prods_cart);

            rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
            rv.setAdapter(adapter);

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            Log.e("airlock_555", e.getMessage());
        }

        return view;
    }
}