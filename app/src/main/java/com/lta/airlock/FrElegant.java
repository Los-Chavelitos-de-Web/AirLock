package com.lta.airlock;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Clases.RelojP;
import RV_RelojItem.Reloj_Adapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrElegant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrElegant extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FrElegant() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrElegant.
     */
    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fr_elegant, container, false);
        RecyclerView rv1 = view.findViewById(R.id.rvRelojHan);
        RecyclerView rv2 = view.findViewById(R.id.rvRelojCur);
        List<RelojP> items = new ArrayList<>();
        items.add(new RelojP("RELOJ 1", "COMUN", Uri.EMPTY, 20.5));
        items.add(new RelojP("RELOJ 2", "COMUN", Uri.EMPTY, 20.5));
        items.add(new RelojP("RELOJ 3", "COMUN", Uri.EMPTY, 20.5));

        rv1.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv1.setAdapter(new Reloj_Adapter(getContext(), items));

        rv2.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv2.setAdapter(new Reloj_Adapter(getContext(), items));

        // Inflate the layout for this fragment
        return view;

    }
}