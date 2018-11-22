package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorpaHranaStavka;

public class KorpaStavkaDialogFragment extends DialogFragment {

    private static String BUNDLE_KORPA_STAVKA = "bundleKorpaStavkaKey";
    private KorpaHranaStavka stavka;

    private TextView textStavkaKorpaDialogTitle,
            textStavkaKorpaDialogNaziv,
            textStavkaKorpaDialogOpis,
            textStavkaKorpaDialogCijena,
            textStavkaKorpaDialogKolicina;

    private Button btnStavkaKorpaDialogUkloni;
    private ImageButton btnStavkaKorpaDialogNazad;
    private ImageView imageStavkaKorpaDialog;

    public static KorpaStavkaDialogFragment newInstance(KorpaHranaStavka obj) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KORPA_STAVKA, obj);
        KorpaStavkaDialogFragment fragment = new KorpaStavkaDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogsTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_korpa_stavka, container, false);

        stavka = (KorpaHranaStavka) getArguments().getSerializable(BUNDLE_KORPA_STAVKA);

        textStavkaKorpaDialogTitle = view.findViewById(R.id.textStavkaKorpaDialogTitle);
        textStavkaKorpaDialogTitle.setText(stavka.getHranaItemVM().getNaziv());

        textStavkaKorpaDialogNaziv = view.findViewById(R.id.textStavkaKorpaDialogNaziv);
        textStavkaKorpaDialogNaziv.setText(stavka.getHranaItemVM().getNaziv());

        textStavkaKorpaDialogKolicina = view.findViewById(R.id.textStavkaKorpaDialogKolicina);
        textStavkaKorpaDialogKolicina.setText(Integer.toString(stavka.getKolicina()));

        textStavkaKorpaDialogOpis = view.findViewById(R.id.textStavkaKorpaDialogOpis);
        textStavkaKorpaDialogOpis.setText(stavka.getHranaItemVM().getOpis());

        imageStavkaKorpaDialog = view.findViewById(R.id.imageStavkaKorpaDialog);
        Glide.with(getActivity())
                .load(stavka.getHranaItemVM().getImageUrl())
                .centerCrop()
                .into(imageStavkaKorpaDialog);

        btnStavkaKorpaDialogNazad = view.findViewById(R.id.btnStavkaKorpaDialogNazad);
        btnStavkaKorpaDialogNazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnStavkaKorpaDialogUkloni = view.findViewById(R.id.btnStavkaKorpaDialogUkloni);
        btnStavkaKorpaDialogUkloni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stavka.umanjiKolicinu();
                // deal with this
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
