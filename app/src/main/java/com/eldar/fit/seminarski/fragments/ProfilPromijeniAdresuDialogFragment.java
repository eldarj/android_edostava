package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.BlokVM;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

import java.util.List;

public class ProfilPromijeniAdresuDialogFragment extends DialogFragment {

    private TextInputEditText textProfilOpcijeAdresaNew;
    private Spinner spinnerProfilOpcijeAdresaBlok;
    private Button btnProfilAdresaOdustani;
    private Button btnProfilAdresaSnimi;
    private KorisnikVM korisnik;
    private TextView tvProfilOpcijeTrenutnaAdresa;

    public static ProfilPromijeniAdresuDialogFragment newInstance(){
        ProfilPromijeniAdresuDialogFragment fragment = new ProfilPromijeniAdresuDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AlertDialogsTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_promijeni_adresu_dialog, container, false);

        korisnik = MySession.getKorisnik();

        tvProfilOpcijeTrenutnaAdresa = view.findViewById(R.id.tvProfilOpcijeTrenutnaAdresa);
        tvProfilOpcijeTrenutnaAdresa.setText(korisnik.getAdresa() + "\n" + korisnik.getBlokVM().getNaziv() + ", " + korisnik.getBlokVM().getOpstina().getnaziv());

        textProfilOpcijeAdresaNew = view.findViewById(R.id.textProfilOpcijeAdresaNew);

        spinnerProfilOpcijeAdresaBlok = view.findViewById(R.id.spinnerProfilOpcijeAdresaBlok);
        List<String> blokPodaci = Storage.getStringListBlokovi();
        MyUtils.popuniSpinner(getActivity(),
                blokPodaci,
                android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerProfilOpcijeAdresaBlok);

        spinnerProfilOpcijeAdresaBlok.setSelection(korisnik.getBlokVM().getId());

        btnProfilAdresaOdustani = view.findViewById(R.id.btnProfilAdresaOdustani);
        btnProfilAdresaOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnProfilAdresaSnimi = view.findViewById(R.id.btnProfilAdresaSnimi);
        btnProfilAdresaSnimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (textProfilOpcijeAdresaNew == null || textProfilOpcijeAdresaNew.length() <= 4) {
                    textProfilOpcijeAdresaNew.setError(getString(R.string.profil_error_adresa));
                    Snackbar.make(getView(), "Molimo provjerite unesene podatke!" , Snackbar.LENGTH_SHORT).show();
                    return;
                }

                korisnik.setAdresa(textProfilOpcijeAdresaNew.getText().toString());
                BlokVM blok = Storage.getBlokovi().get(spinnerProfilOpcijeAdresaBlok.getSelectedItemPosition());
                korisnik.setBlokVM(blok);
                MySession.setKorisnik(korisnik);

                Snackbar.make(getView(), "Uspješno ste sačuvali adresu!" , Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        getDialog().dismiss();
                    }
                }).show();
            }
        });

        return view;
    }
}
