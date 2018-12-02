package com.eldar.fit.seminarski.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.Snackbar;
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
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;
import com.eldar.fit.seminarski.data.NarudzbaPrikazVM;
import com.eldar.fit.seminarski.data.NarudzbaStavkaVM;
import com.eldar.fit.seminarski.data.NarudzbaVM;
import com.eldar.fit.seminarski.data.NarudzbaPregledVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;

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
    private View view;

    public static ProfilNarudzbeFragment newInstance() {
        ProfilNarudzbeFragment fragment = new ProfilNarudzbeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profil_narudzbe_fragment, container, false);

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
        AuthLogin credentialsObj = new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword());
        MyApiRequest.post(getActivity(), MyApiRequest.ENDPOINT_NARUDZBE, credentialsObj, new MyAbstractRunnable<NarudzbaPrikazVM>() {
            @Override
            public void run(NarudzbaPrikazVM narudzbaPrikazVM) {
                onNarudzbaListReceived(narudzbaPrikazVM, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                onNarudzbaListReceived(null, statusCode, errorMessage);
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

    private void onNarudzbaListReceived(@Nullable NarudzbaPrikazVM narudzbePodaci, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_narudzbaList) != null) {
            view.findViewById(R.id.progressBar_narudzbaList).setVisibility(View.INVISIBLE);
        }

        if (narudzbePodaci != null) {
            podaci = narudzbePodaci.narudzbe;
            popuniPodatke();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                    errorMessage != null ? errorMessage : "Dogodila se greška.",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void popuniPodatke() {
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

                Set<String> restorani = new HashSet<String>(n.getNarucenoIzRestorana());
                ChipGroup chipGroupRestorani = view.findViewById(R.id.chipgroupStavkaNarudzbaRestorani);
                chipGroupRestorani.removeAllViews();
                for (String restoranNaziv :
                        restorani) {
                    Chip c = new Chip(getActivity());
                    c.setText(restoranNaziv);
                    chipGroupRestorani.addView(c);
                }

                Set<NarudzbaStavkaVM> hrana = new HashSet<NarudzbaStavkaVM>(n.getNarudzbaStavke());
                ChipGroup chipGroupHrana = view.findViewById(R.id.chipgroupStavkaNarudzbaHrana);
                chipGroupHrana.removeAllViews();
                for (NarudzbaStavkaVM stavka :
                        hrana) {
                    Chip c = new Chip(getActivity());
                    c.setText(stavka.getNaziv());
                    chipGroupHrana.addView(c);
                }

                return view;
            }
        };

        listViewNarudzbe.setAdapter(listAdapter);
    }
}
