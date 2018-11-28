package com.eldar.fit.seminarski.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;
import com.eldar.fit.seminarski.data.NarudzbaVM;
import com.eldar.fit.seminarski.data.NarudzbaPregledVM;
import com.eldar.fit.seminarski.data.RestoranVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyGson;
import com.eldar.fit.seminarski.helper.MyUrlConnection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfilNarudzbeFragment extends Fragment {

    private ImageButton btnNarudzbeClose;
    private SearchView searchViewNarudzbe;
    private ListView listViewNarudzbe;
    private BaseAdapter listNarudzbeAdapter;
    private List<NarudzbaVM> podaci;
    private NarudzbaPregledVM podaci2;

    public static ProfilNarudzbeFragment newInstance() {
        ProfilNarudzbeFragment fragment = new ProfilNarudzbeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_narudzbe_fragment, container, false);

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

        // ASYNC
        podaci = Storage.getNarudzbe();
        podaci2 = null;

        MyUrlConnection.volleyGet("http://ekstra.ba/api/narudzbe.php");

        new AsyncTask<Void, Void, NarudzbaPregledVM>() {
            @Override
            protected NarudzbaPregledVM doInBackground(Void... voids) {
                String strJson = MyUrlConnection.Get("http://ekstra.ba/api" + "/narudzbe.php");
                NarudzbaPregledVM result = MyGson.build().fromJson(strJson, NarudzbaPregledVM.class);

                return result;
            }

            @Override
            protected void onPostExecute(NarudzbaPregledVM narudzbaVMS) {
                podaci2 = narudzbaVMS;
                listViewNarudzbe.setAdapter(getListNarudzbeAdapter(podaci2));
            }
        }.execute();


        listViewNarudzbe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
            }
        });


        return view;
    }

    private BaseAdapter getListNarudzbeAdapter(NarudzbaPregledVM narudzbaPregled) {

        return new BaseAdapter() {
            @Override
            public int getCount() {
                return narudzbaPregled.rows.size();
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
                    view = inflater != null ? inflater.inflate(R.layout.profil_narudzba_stavka, parent, false) : null;
                }
                NarudzbaPregledVM.Row n = narudzbaPregled.rows.get(position);

                TextView textStavkaNarudzbaSifra = view.findViewById(R.id.textStavkaNarudzbaSifra);
                textStavkaNarudzbaSifra.setText(n.getUuid().toString());

                TextView textStavkaNarudzbaDatum = view.findViewById(R.id.textStavkaNarudzbaDatum);
                textStavkaNarudzbaDatum.setText("Datum kreiranja " + n.getDatumKreiranja());

                TextView textStavkaNarudzbaCijena = view.findViewById(R.id.textStavkaNarudzbaCijena);
                textStavkaNarudzbaCijena.setText(String.format("%1$,.2f KM", n.getCijena()));

//                Set<RestoranVM> restorani = new HashSet<RestoranVM>(n.getRestorani());
//                ChipGroup chipGroupRestorani = view.findViewById(R.id.chipgroupStavkaNarudzbaRestorani);
//                chipGroupRestorani.removeAllViews();
//                for (RestoranVM restoran :
//                        restorani) {
//                    Chip c = new Chip(getActivity());
//                    c.setText(restoran.getNaziv());
//                    chipGroupRestorani.addView(c);
//                }

//                Set<KorpaHranaStavka> hrana = new HashSet<KorpaHranaStavka>(n.getHrana());
//                ChipGroup chipGroupHrana = view.findViewById(R.id.chipgroupStavkaNarudzbaHrana);
//                chipGroupHrana.removeAllViews();
//                for (KorpaHranaStavka stavka :
//                        hrana) {
//                    Chip c = new Chip(getActivity());
//                    c.setText(stavka.getHranaItemVM().getNaziv());
//                    chipGroupHrana.addView(c);
//                }

                return view;
            }
        };
    }

    private BaseAdapter prepListNarudzbeAdapter(List<NarudzbaVM> podaci) {
        return new BaseAdapter() {
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
                    view = inflater != null ? inflater.inflate(R.layout.profil_narudzba_stavka, parent, false) : null;
                }
                NarudzbaVM n = podaci.get(position);

                TextView textStavkaNarudzbaSifra = view.findViewById(R.id.textStavkaNarudzbaSifra);
                textStavkaNarudzbaSifra.setText(n.getuId().toString());

                TextView textStavkaNarudzbaDatum = view.findViewById(R.id.textStavkaNarudzbaDatum);
                textStavkaNarudzbaDatum.setText("Datum kreiranja " + n.getDatumNapravljenaString());

                TextView textStavkaNarudzbaCijena = view.findViewById(R.id.textStavkaNarudzbaCijena);
                textStavkaNarudzbaCijena.setText(String.format("%1$,.2f KM", n.getUkupnaCijena()));

                Set<RestoranVM> restorani = new HashSet<RestoranVM>(n.getRestorani());
                ChipGroup chipGroupRestorani = view.findViewById(R.id.chipgroupStavkaNarudzbaRestorani);
                chipGroupRestorani.removeAllViews();
                for (RestoranVM restoran :
                        restorani) {
                    Chip c = new Chip(getActivity());
                    c.setText(restoran.getNaziv());
                    chipGroupRestorani.addView(c);
                }

                Set<KorpaHranaStavka> hrana = new HashSet<KorpaHranaStavka>(n.getHranaStavke());
                ChipGroup chipGroupHrana = view.findViewById(R.id.chipgroupStavkaNarudzbaHrana);
                chipGroupHrana.removeAllViews();
                for (KorpaHranaStavka stavka :
                        hrana) {
                    Chip c = new Chip(getActivity());
                    c.setText(stavka.getHranaItemVM().getNaziv());
                    chipGroupHrana.addView(c);
                }

                return view;
            }
        };
    }
}
