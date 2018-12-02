package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.HranaItemVM;
import com.eldar.fit.seminarski.data.HranaPrikazVM;
import com.eldar.fit.seminarski.data.Korpa;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.RestoranInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestoranJelovnikFragment extends Fragment {

    private static final String JELOVNIK_RESTORANA = "jelovnikRestorana";

    private ListView listViewHrana;
    private List<HranaItemVM> podaci;
    private Korpa korpa;
    private View view;
    private RestoranInfo restoran;

    public static RestoranJelovnikFragment newInstance(RestoranInfo restoran) {
        RestoranJelovnikFragment fragment = new RestoranJelovnikFragment();

        Bundle args = new Bundle();
        args.putSerializable(JELOVNIK_RESTORANA, restoran);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(JELOVNIK_RESTORANA)) {
            restoran = (RestoranInfo) getArguments().getSerializable(JELOVNIK_RESTORANA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getKorpaSession();
        view = inflater.inflate(R.layout.restoran_jelovnik_fragment, container, false);

        listViewHrana = view.findViewById(R.id.listViewHrana);

        MyApiRequest.get(getActivity(),
                String.format(MyApiRequest.ENDPOINT_RESTORANI_HRANA, restoran.getId()),
                new MyAbstractRunnable<HranaPrikazVM>() {
            @Override
            public void run(HranaPrikazVM hranaPrikazVM) {
                onHranaListReceived(hranaPrikazVM, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                onHranaListReceived(null, statusCode, errorMessage);
            }
        });

        return view;
    }

    private void onHranaListReceived(@Nullable HranaPrikazVM hranaPodaci, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_hranaList) != null) {
            view.findViewById(R.id.progressBar_hranaList).setVisibility(View.INVISIBLE);
        }

        if (hranaPodaci != null) {
            podaci = hranaPodaci.hrana;
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
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.restoran_jelovnik_stavka, parent, false) : null;
                }
                TextView textStavkaJelovnikNaziv = view.findViewById(R.id.textStavkaJelovnikNaziv);
                textStavkaJelovnikNaziv.setText(podaci.get(position).getNaziv());

                TextView textStavkaJelovnikOpis = view.findViewById(R.id.textStavkaJelovnikOpis);
                textStavkaJelovnikOpis.setText(podaci.get(position).getOpis());

                TextView textStavkaJelovnikCijena = view.findViewById(R.id.textStavkaJelovnikCijena);
                textStavkaJelovnikCijena.setText(String.format("%1$,.2f KM",podaci.get(position).getCijena()));

                ImageButton btnDodajStavkuKorpu = view.findViewById(R.id.btnDodajStavkuKorpu);
                btnDodajStavkuKorpu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackground(getResources().getDrawable(R.drawable.ic_plus_round_added));
                        do_dodajStavku(podaci.get(position));
                    }
                });

                CircleImageView imageStavkaJelovnikSlika = view.findViewById(R.id.imageStavkaJelovnikSlika);
                if (podaci.get(position).getImageUrl() == null) {
                    //imageStavkaJelovnikSlika.setImageDrawable(getResources().getDrawable(R.drawable.ic_round_placeholder_image));
                } else {
                    Glide.with(getActivity())
                            .load(podaci.get(position).getImageUrl())
                            .centerCrop()
                            .into(imageStavkaJelovnikSlika);
                }

                return view;
            }
        };

        listViewHrana.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        getKorpaSession();
        super.onResume();
    }

    private void getKorpaSession() {
        if (MySession.getKorpa() == null) {
            MySession.setKorpa(new Korpa());
        }
        korpa = MySession.getKorpa();
    }

    private void do_dodajStavku(HranaItemVM stavka) {
        korpa.dodajStavku(stavka);
        MySession.setKorpa(korpa);
    }
}
