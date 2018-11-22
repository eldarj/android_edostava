package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.RestoranDetaljnoActivity;
import com.eldar.fit.seminarski.data.Korpa;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;
import com.eldar.fit.seminarski.helper.MySession;

import java.io.Serializable;

public class KorpaFragment extends Fragment {

    private Korpa korpa;
    private TextView textKorpaIntro, textKorpaTotal;
    private ListView listKorpaStavke;

    public static KorpaFragment newInstance() {
        Bundle args = new Bundle();
        KorpaFragment fragment = new KorpaFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        ((RestoranDetaljnoActivity) getActivity()).getSupportActionBar().hide();
//        Log.i("test", "hide");

        getKorpaSession();

        super.onCreate(savedInstanceState);
    }

    private void getKorpaSession() {
        if (MySession.getKorpa() == null) {
            korpa = new Korpa();
            MySession.setKorpa(korpa);
        }
        korpa = MySession.getKorpa();
    }

    @Override
    public void onResume() {
        getKorpaSession();
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_korpa, container, false);

        textKorpaIntro = view.findViewById(R.id.textKorpaIntro);
        textKorpaIntro.setText(korpa.getHranaStavke().size() == 0 ?
                "" + korpa.getHranaStavke().size() + " stavke u vašoj korpi" :
                "Korpa je prazna. Pregledajte restorane i jelovnike, izaberite i dodajte nešto u korpu!");

        textKorpaTotal = view.findViewById(R.id.textKorpaTotal);
        final double ukupno = korpa.getUkupnaCijena();
        textKorpaTotal.setText(ukupno == 0 ? "- KM" : ukupno + " KM");

        listKorpaStavke = view.findViewById(R.id.listKorpaStavke);
        listKorpaStavkePopuni();


        return view;
    }

    private void listKorpaStavkePopuni() {
        // podaci -> korpa.getHranaStavke()
        BaseAdapter listStavkeAdapter = new BaseAdapter() {
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
                view = getLayoutInflater().inflate(R.layout.stavka_korpa, parent, false);

                KorpaHranaStavka stavka = korpa.getHranaStavke().get(position);

                TextView stavka_korpa_super = view.findViewById(R.id.stavka_korpa_super);
                stavka_korpa_super.setText("Iz restorana " + stavka.getHranaItemVM().getRestoran().getNaziv());

                TextView stavka_korpa_title = view.findViewById(R.id.stavka_korpa_title);
                stavka_korpa_title.setText(stavka.getHranaItemVM().getNaziv());

                TextView stavka_korpa_subtitle = view.findViewById(R.id.stavka_korpa_subtitle);
                stavka_korpa_subtitle.setText(
                        "Jed.cijena " + stavka.getHranaItemVM().getCijena() +
                                " Kolicina " + stavka.getKolicina()
                );

                TextView stavka_korpa_cijena = view.findViewById(R.id.stavka_korpa_cijena);
                stavka_korpa_cijena.setText(stavka.getUkupnaCijena() == 0 ? "- KM" : stavka.getUkupnaCijena() + " KM");

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
}
