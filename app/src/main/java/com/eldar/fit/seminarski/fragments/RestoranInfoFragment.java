package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.RestoranVM;
import com.eldar.fit.seminarski.helper.RestoranInfo;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;

public class RestoranInfoFragment extends Fragment {

    TextView restoranNaziv, restoranOpis, restoranLikesCount, titleDetaljnoRestoranNaziv;
    ImageView restoranSlika;

    private RestoranInfo restoran;
    private TextView restoranAdresa;
    private TextView restoranLokacija;
    private TextView restoranTelefon;
    private TextView restoranWeb;
    private TextView restoranEmail;
    private TextView vlasnikImePrezime;
    private TextView vlasnikEmail;

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

        // Restoran top-section
        restoranNaziv = view.findViewById(R.id.textDetaljnoRestoranNaziv);
        restoranNaziv.setText(restoran.getNaziv());

        titleDetaljnoRestoranNaziv = view.findViewById(R.id.titleDetaljnoRestoranNaziv);
        titleDetaljnoRestoranNaziv.setText(restoran.getNaziv());

        restoranOpis = view.findViewById(R.id.textDetaljnoRestoranOpis);
        restoranOpis.setText(restoran.getOpis());

        restoranLikesCount = view.findViewById(R.id.textDetaljnoRestoranLikes);
        restoranLikesCount.setText(restoran.getLikesCount() + " sviđanja");

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
