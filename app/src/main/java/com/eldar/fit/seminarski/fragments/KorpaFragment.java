package com.eldar.fit.seminarski.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.Korpa;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;

public class KorpaFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Korpa korpa;
    private TextView textKorpaIntro, textKorpaTotal;
    private ListView listKorpaStavke;
    private Button btnKorpaNaruci;
    private MaterialButton btnKorpaOdbaci;
    private BaseAdapter listStavkeAdapter;
    private ImageButton btnKorpaClose;

    public static KorpaFragment newInstance() {
        Bundle args = new Bundle();
        KorpaFragment fragment = new KorpaFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getKorpaSession();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_korpa, container, false);

        Log.i("Test", "onCreateView::KorpaFragment");

        textKorpaIntro = view.findViewById(R.id.textKorpaIntro);
        textKorpaIntro.setText(korpa.getHranaStavke().size() == 0 ?
                "Korpa je prazna. Pregledajte restorane i jelovnike, izaberite i dodajte nešto u korpu!" :
                "Ukupno stavki u korpi: " + korpa.getHranaStavkeTotalCount());

        textKorpaTotal = view.findViewById(R.id.textKorpaTotal);
        final double ukupno = korpa.getUkupnaCijena();
        textKorpaTotal.setText(ukupno == 0 ? "- KM" : ukupno + " KM");

        listKorpaStavke = view.findViewById(R.id.listKorpaStavke);
        listKorpaStavkePopuni();
        listKorpaStavke.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KorpaHranaStavka stavka = korpa.getHranaStavke().get(position);
                MyFragmentHelper.dodajDialog((AppCompatActivity) getActivity(), "korpaStavkaDlg", KorpaStavkaDialogFragment.newInstance(stavka));
            }
        });

        btnKorpaClose = view.findViewById(R.id.btnKorpaClose);
        btnKorpaClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnKorpaOdbaci = view.findViewById(R.id.btnKorpaOdbaci);
        btnKorpaOdbaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Korpa.kreirajNarudzbu(korpa);
                korpa = new Korpa();
                MySession.setKorpa(korpa);
            }
        });

        btnKorpaNaruci = view.findViewById(R.id.btnKorpaNaruci);
        btnKorpaNaruci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                korpa = Korpa.odbaciNarudzbu();
            }
        });

        return view;
    }

    private void getKorpaSession() {
        if (listStavkeAdapter != null ) {
            listStavkeAdapter.notifyDataSetChanged();
        }

        if (MySession.getKorpa() == null) {
            korpa = new Korpa();
            MySession.setKorpa(korpa);
        }
        korpa = MySession.getKorpa();
    }

    private void listKorpaStavkePopuni() {
        // podaci -> korpa.getHranaStavke()
        listStavkeAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return korpa.getHranaStavke().size();
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
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.stavka_korpa, parent, false) : null;
                }

                KorpaHranaStavka stavka = korpa.getHranaStavke().get(position);

                TextView textStavkaKorpaSuper = view.findViewById(R.id.textStavkaKorpaSuper);
                textStavkaKorpaSuper.setText("Restoran " + stavka.getHranaItemVM().getRestoran().getNaziv());

                TextView textStavkaKorpaNaziv = view.findViewById(R.id.textStavkaKorpaNaziv);
                textStavkaKorpaNaziv.setText("x" + stavka.getKolicina() + " " + stavka.getHranaItemVM().getNaziv());

                TextView textStavkaKorpaOpis = view.findViewById(R.id.textStavkaKorpaOpis);
                textStavkaKorpaOpis.setText("Cijena " + stavka.getHranaItemVM().getCijena());

                TextView textStavkaKorpaCijena = view.findViewById(R.id.textStavkaKorpaCijena);
                textStavkaKorpaCijena.setText(stavka.getUkupnaCijena() == 0 ? "- KM" : stavka.getUkupnaCijena() + " KM");

                ImageView imageStavkaKorpa = view.findViewById(R.id.imageStavkaKorpa);
                Glide.with(getActivity())
                        .load(stavka.getHranaItemVM().getImageUrl())
                        .centerCrop()
                        .into(imageStavkaKorpa);

                return view;
            }
        };

        listKorpaStavke.setAdapter(listStavkeAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_korpa_secondary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionMojeNarudzbe) {
            MyFragmentHelper.fragmentCreate((AppCompatActivity) getActivity(), R.id.fragmentContainer, ProfilNarudzbeFragment.newInstance());
        }

        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("Test", "onSharedPreferenceChanged with key: " + key);
    }

    @Override
    public void onResume() {
        super.onResume();
        getKorpaSession();

        SharedPreferences prefs = MySession.getPrefs();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        SharedPreferences prefs = MySession.getPrefs();
        prefs.registerOnSharedPreferenceChangeListener(this);

        super.onPause();
    }
}
