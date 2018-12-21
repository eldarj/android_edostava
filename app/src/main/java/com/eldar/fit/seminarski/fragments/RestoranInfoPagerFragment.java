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
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.TipKuhinje;
import com.eldar.fit.seminarski.data.RestoranInfo;

public class RestoranInfoPagerFragment extends Fragment {
    private static final String DETALJNO_PAGER_RESTORAN_OBJ = "detaljnoPagerRestoranObj";
    public static final String PAGE_TITLE = "O nama";

    private TextView restoranOpis;
    private TextView restoranAdresa;
    private TextView restoranLokacija;
    private TextView restoranTelefon;
    private TextView restoranWeb;
    private TextView restoranEmail;
    private TextView vlasnikImePrezime;
    private TextView vlasnikEmail;
    private ChipGroup chipGroupRestoranTipovihrane;
    private RestoranInfo restoran;

    public static RestoranInfoPagerFragment newInstance(RestoranInfo restoranInfo) {
        RestoranInfoPagerFragment fragment = new RestoranInfoPagerFragment();

        Bundle args = new Bundle();
        args.putSerializable(DETALJNO_PAGER_RESTORAN_OBJ, restoranInfo);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(DETALJNO_PAGER_RESTORAN_OBJ)) {
            restoran = (RestoranInfo) getArguments().getSerializable(DETALJNO_PAGER_RESTORAN_OBJ);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restoran_info_pager_fragment, container, false);

        restoranOpis = view.findViewById(R.id.textDetaljnoRestoranOpis);
        restoranOpis.setText(restoran.getOpis());

        chipGroupRestoranTipovihrane = view.findViewById(R.id.chipGroupRestoranTipovihrane);
        prepareChipGroupTipoviHrane();


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

    private void prepareChipGroupTipoviHrane() {
        chipGroupRestoranTipovihrane.removeAllViews();
        chipGroupRestoranTipovihrane.setFocusable(false);
        try {
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
    }
}
