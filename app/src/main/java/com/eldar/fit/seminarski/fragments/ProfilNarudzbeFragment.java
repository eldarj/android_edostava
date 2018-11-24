package com.eldar.fit.seminarski.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.NarudzbaVM;
import com.eldar.fit.seminarski.data.Storage;

import java.util.List;
import java.util.zip.Inflater;

public class ProfilNarudzbeFragment extends Fragment {

    private ImageButton btnNarudzbeClose;
    private SearchView searchViewNarudzbe;
    private ListView listViewNarudzbe;

    public static ProfilNarudzbeFragment newInstance() {
        ProfilNarudzbeFragment fragment = new ProfilNarudzbeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_narudzbe, container, false);

        btnNarudzbeClose = view.findViewById(R.id.btnNarudzbeClose);
        btnNarudzbeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        searchViewNarudzbe = view.findViewById(R.id.searchViewNarudzbe);
        //

        listViewNarudzbe = view.findViewById(R.id.listViewNarudzbe);

        List<NarudzbaVM> podaci = Storage.getNarudzbe();
        listViewNarudzbe.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater != null ? inflater.inflate(R.layout.stavka_narudzba, parent, false) : null;
                }

                return view;
            }
        });

        listViewNarudzbe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });


        return view;
    }
}
