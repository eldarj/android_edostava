package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.HranaItemVM;
import com.eldar.fit.seminarski.data.Storage;

import java.util.List;

public class RestoranHranaFragment extends Fragment {

    ListView listViewHrana;
    private List<HranaItemVM> podaci;

    public static RestoranHranaFragment newInstance() {
        RestoranHranaFragment fragment = new RestoranHranaFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restoran_hrana, container, false);

        listViewHrana = view.findViewById(R.id.listViewHrana);

        popuniPodatke();

        return view;
    }

    private void popuniPodatke() {
        podaci = Storage.getHrana();

        BaseAdapter listAdapter = new BaseAdapter() {
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
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.stavka_restoran_hrana, parent, false) : null;
                }

                TextView title = view.findViewById(R.id.textStavkaHranaTitle);
                title.setText(podaci.get(position).getNaziv());

                return view;
            }
        };

        listViewHrana.setAdapter(listAdapter);
    }
}
