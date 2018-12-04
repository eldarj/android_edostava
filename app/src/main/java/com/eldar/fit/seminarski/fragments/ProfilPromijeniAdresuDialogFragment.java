package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.ApiBlokList;
import com.eldar.fit.seminarski.data.AuthRegister;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.ApiResponseHandler;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

import java.util.List;

public class ProfilPromijeniAdresuDialogFragment extends DialogFragment {

    public static String Tag = "profilPromijeniAdresuDialogFragment";

    private TextInputEditText textProfilOpcijeAdresaNew;
    private Spinner spinnerProfilOpcijeAdresaBlok;
    private Button btnProfilAdresaOdustani;
    private Button btnProfilAdresaSnimi;
    private KorisnikVM korisnik;
    private TextView tvProfilOpcijeTrenutnaAdresa;
    private View view;

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
        view = inflater.inflate(R.layout.profil_promijeni_adresu_dialog, container, false);

        korisnik = MySession.getKorisnik();

        tvProfilOpcijeTrenutnaAdresa = view.findViewById(R.id.tvProfilOpcijeTrenutnaAdresa);
        tvProfilOpcijeTrenutnaAdresa.setText(korisnik.getAdresa() + ", " + korisnik.getLokacija());

        textProfilOpcijeAdresaNew = view.findViewById(R.id.textProfilOpcijeAdresaNew);

        spinnerProfilOpcijeAdresaBlok = view.findViewById(R.id.spinnerProfilOpcijeAdresaBlok);

        MyApiRequest.get(getActivity(), MyApiRequest.ENDPOINT_LOCATIONS, new MyAbstractRunnable<ApiBlokList>() {
            @Override
            public void run(ApiBlokList apiBlokList) {
                List<String> blokPodaci = ApiResponseHandler.getStringListBlokovi(apiBlokList);
                onBlokPodaciReceived(blokPodaci, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                onBlokPodaciReceived(null, statusCode, errorMessage);
            }
        });

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
                if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
                    view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.VISIBLE);
                }

                if (textProfilOpcijeAdresaNew == null || textProfilOpcijeAdresaNew.length() <= 4) {
                    textProfilOpcijeAdresaNew.setError(getString(R.string.profil_error_adresa));
                    Snackbar.make(getView(), "Molimo provjerite unesene podatke!" , Snackbar.LENGTH_SHORT).show();
                    return;
                }

                AuthRegister userPostObj = new AuthRegister(korisnik);
                userPostObj.setAdresa(textProfilOpcijeAdresaNew.getText().toString());
                userPostObj.setBlokID(ApiResponseHandler.getBlokovi().get(spinnerProfilOpcijeAdresaBlok.getSelectedItemPosition()).getId());

                MyApiRequest.post(getActivity(), MyApiRequest.ENDPOINT_USER_UPDATE_AUTH, userPostObj, new MyAbstractRunnable<KorisnikVM>() {
                    @Override
                    public void run(KorisnikVM korisnikVM) {
                        Log.i("Test", "run, result: " + korisnikVM.getAdresa());
                        updateLokacija(korisnikVM, null, null);
                    }

                    @Override
                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                        Log.i("Test", "run, result: " + statusCode + errorMessage);
                        updateLokacija(null, statusCode, errorMessage);
                    }
                });
            }
        });

        return view;
    }

    private void updateLokacija(@Nullable KorisnikVM korisnik, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
            view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.INVISIBLE);
        }

        if (korisnik == null) {
            Snackbar.make(getView(), "Dogodila s greška, provjerite podatke i pokušajte ponovo." , Snackbar.LENGTH_SHORT).show();
        } else {
            MySession.setKorisnik(korisnik);
            Snackbar.make(getView(), "Uspješno ste sačuvali adresu!" , Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    getDialog().dismiss();
                }
            }).show();
        }
    }

    private void onBlokPodaciReceived(@Nullable List<String> blokPodaci, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_blokSpinner) != null) {
            view.findViewById(R.id.progressBar_blokSpinner).setVisibility(View.INVISIBLE);
        }

        if (blokPodaci != null) {
            MyUtils.popuniSpinner(getActivity(),
                    blokPodaci,
                    android.R.layout.simple_spinner_item,
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerProfilOpcijeAdresaBlok);

            spinnerProfilOpcijeAdresaBlok.setSelection(blokPodaci.indexOf(korisnik.getLokacija()));
        } else {
            Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                    errorMessage != null ? errorMessage : "Dogodila se greška.",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
