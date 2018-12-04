package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.TipKuhinje;
import com.eldar.fit.seminarski.helper.RestoranInfo;

import java.util.ArrayList;
import java.util.List;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;

public class RestoranInfoFragment extends Fragment {
    public static String Tag = "restoranInfoFragment";

    private RestoranInfo restoran;

    private ImageButton btnRestoranClose;
    private ImageView restoranSlika;

    private TextView restoranNaziv;
    private TextView restoranOpis;
    private TextView restoranStatsCount;
    private TextView titleDetaljnoRestoranNaziv;
    private TextView restoranAdresa;
    private TextView restoranLokacija;
    private TextView restoranTelefon;
    private TextView restoranWeb;
    private TextView restoranEmail;
    private TextView vlasnikImePrezime;
    private TextView vlasnikEmail;
    private ChipGroup chipGroupRestoranTipovihrane;

    public static RestoranInfoFragment newInstance(RestoranInfo restoran) {
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_VIEW_RESTORAN, restoran);

        RestoranInfoFragment fragment = new RestoranInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restoran_info_fragment, container, false);
        restoran = (RestoranInfo) getArguments().getSerializable(DETAIL_VIEW_RESTORAN);

        btnRestoranClose = view.findViewById(R.id.btnRestoranClose);
        btnRestoranClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Restoran top-section
        restoranNaziv = view.findViewById(R.id.textDetaljnoRestoranNaziv);
        restoranNaziv.setText(restoran.getNaziv());

        titleDetaljnoRestoranNaziv = view.findViewById(R.id.titleDetaljnoRestoranNaziv);
        titleDetaljnoRestoranNaziv.setText(restoran.getNaziv());

        restoranOpis = view.findViewById(R.id.textDetaljnoRestoranOpis);
        restoranOpis.setText(restoran.getOpis());

        restoranStatsCount = view.findViewById(R.id.textDetaljnoRestoranStats);
        restoranStatsCount.setText(restoran.getLikesCount() + " sviđanja - " + restoran.getRecenzije().size() + " recenzija");

        try {
            chipGroupRestoranTipovihrane = view.findViewById(R.id.chipGroupRestoranTipovihrane);
            chipGroupRestoranTipovihrane.removeAllViews();
            chipGroupRestoranTipovihrane.setFocusable(false);
            for (TipKuhinje t:
                 restoran.getTipoviKuhinje()) {
                Chip c = new Chip(getActivity());
                c.setText(t.naziv);
                c.setFocusable(false);
                c.setClickable(false);
                chipGroupRestoranTipovihrane.addView(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        restoranSlika = view.findViewById(R.id.imageDetaljnoRestoranSlika);
        Glide.with(this)
                .load(restoran.getSlika())
                .centerCrop()
                .into(restoranSlika);

        // Kontakt section
        restoranAdresa = view.findViewById(R.id.textDetaljnoRestoranAdresa);
        restoranAdresa.setText(restoran.getAdresa());

        restoranLokacija = view.findViewById(R.id.textDetaljnoRestoranLokacija);
        restoranLokacija.setText(restoran.getLokacija());

        restoranTelefon = view.findViewById(R.id.textDetaljnoRestoranTelefon);
        restoranTelefon.setText(restoran.getTelefon());

        restoranWeb = view.findViewById(R.id.textDetaljnoRestoranWebUrl);
        restoranWeb.setText(restoran.getWebUrl());

        restoranEmail = view.findViewById(R.id.textDetaljnoRestoranEmail);
        restoranEmail.setText(restoran.getEmail());

        // Vlasnik section
        vlasnikImePrezime = view.findViewById(R.id.textDetaljnoRestoranVlasnikImePrezime);
        vlasnikImePrezime.setText(restoran.getVlasnik().getImePrezime());

        vlasnikEmail = view.findViewById(R.id.textDetaljnoRestoranVlasnikEmail);
        vlasnikEmail.setText(restoran.getEmail());

        return view;
    }
}
