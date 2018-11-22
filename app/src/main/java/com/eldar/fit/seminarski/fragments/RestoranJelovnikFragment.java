package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.HranaItemVM;
import com.eldar.fit.seminarski.data.Korpa;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MySession;

import java.util.List;

public class RestoranJelovnikFragment extends Fragment {

    ListView listViewHrana;
    private List<HranaItemVM> podaci;
    private Korpa korpa;

    public static RestoranJelovnikFragment newInstance() {
        RestoranJelovnikFragment fragment = new RestoranJelovnikFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getKorpaSession();
        View view = inflater.inflate(R.layout.fragment_restoran_jelovnik, container, false);

        listViewHrana = view.findViewById(R.id.listViewHrana);

        popuniPodatke();

        return view;
    }

    @Override
    public void onResume() {
        getKorpaSession();
        super.onResume();
    }


    private void getKorpaSession() {
        if (MySession.getKorpa() == null) {
            korpa = new Korpa();
            MySession.setKorpa(korpa);
        }
        korpa = MySession.getKorpa();
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
                    view = inflater != null ? inflater.inflate(R.layout.stavka_restoran_jelovnik, parent, false) : null;
                }

                TextView textStavkaJelovnikNaziv = view.findViewById(R.id.textStavkaJelovnikNaziv);
                textStavkaJelovnikNaziv.setText(podaci.get(position).getNaziv());

                TextView textStavkaJelovnikOpis = view.findViewById(R.id.textStavkaJelovnikOpis);
                textStavkaJelovnikOpis.setText(podaci.get(position).getOpis());

                TextView textStavkaJelovnikSastojci = view.findViewById(R.id.textStavkaJelovnikSastojci);
                StringBuilder sastojci = new StringBuilder("Sastojci ");
                for (String s : podaci.get(position).getSastojci()) {
                    sastojci.append(s).append(", ");
                }
                textStavkaJelovnikSastojci.setText(sastojci);

                TextView textStavkaJelovnikCijena = view.findViewById(R.id.textStavkaJelovnikCijena);
                textStavkaJelovnikCijena.setText(String.format("%d KM", podaci.get(position).getCijena()));

                Button btnDodajStavkuKorpu = view.findViewById(R.id.btnDodajStavkuKorpu);
                btnDodajStavkuKorpu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        korpa.dodajStavku(podaci.get(position));
                    }
                });

                ImageView imageStavkaJelovnikSlika = view.findViewById(R.id.imageStavkaJelovnikSlika);
                Glide.with(getActivity())
                        .load(podaci.get(position).getImageUrl())
                        .centerCrop()
                        .into(imageStavkaJelovnikSlika);

                return view;
            }
        };

        listViewHrana.setAdapter(listAdapter);
    }
}
