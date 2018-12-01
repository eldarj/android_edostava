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

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthRegister;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;

public class ProfilPromijeniLozinkuDialogFragment extends DialogFragment {

    private TextInputEditText textProfilOpcijeLozinkaTrenutna;
    private TextInputEditText textProfilOpcijeLozinkaNewPonovo;
    private TextInputEditText textProfilOpcijeLozinkaNew;
    private Button btnProfilLozinkaOdustani;
    private Button btnProfilLozinkaSnimi;
    private KorisnikVM korisnik;
    private View view;

    public static ProfilPromijeniLozinkuDialogFragment newInstance() {
        ProfilPromijeniLozinkuDialogFragment fragment = new ProfilPromijeniLozinkuDialogFragment();
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
        view = inflater.inflate(R.layout.profil_promijeni_lozinku_dialog, container, false);

        korisnik = MySession.getKorisnik();

        textProfilOpcijeLozinkaTrenutna = view.findViewById(R.id.textProfilOpcijeLozinkaTrenutna);
        textProfilOpcijeLozinkaNewPonovo = view.findViewById(R.id.textProfilOpcijeLozinkaNewPonovo);
        textProfilOpcijeLozinkaNew = view.findViewById(R.id.textProfilOpcijeLozinkaNew);

        btnProfilLozinkaOdustani = view.findViewById(R.id.btnProfilLozinkaOdustani);
        btnProfilLozinkaOdustani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnProfilLozinkaSnimi = view.findViewById(R.id.btnProfilLozinkaSnimi);
        btnProfilLozinkaSnimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
                    view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.VISIBLE);
                }

                if (textProfilOpcijeLozinkaTrenutna == null ||
                        !korisnik.correctLozinka(textProfilOpcijeLozinkaTrenutna.getText().toString())) {
                    // iftrue:
                    textProfilOpcijeLozinkaTrenutna.setError("Netačna lozinka!");
                } else {
                    textProfilOpcijeLozinkaTrenutna.setError(null);
                }

                if (textProfilOpcijeLozinkaNew == null ||
                        textProfilOpcijeLozinkaNewPonovo == null ||
                        textProfilOpcijeLozinkaNew.length() < 6 ||
                        textProfilOpcijeLozinkaNewPonovo.length() < 6  ||
                        !textProfilOpcijeLozinkaNew.getText().toString().equals(textProfilOpcijeLozinkaNewPonovo.getText().toString())) {
                    // iftrue:
                    textProfilOpcijeLozinkaNew.setError("Polje ne smije biti prazno, manje od 6 karaktera ili se lozinke ne podudaraju!");
                    textProfilOpcijeLozinkaNewPonovo.setError("Polje ne smije biti prazno, manje od 6 karaktera ili se lozinke ne podudaraju!");
                } else {
                    textProfilOpcijeLozinkaNew.setError(null);
                    textProfilOpcijeLozinkaNewPonovo.setError(null);
                }

                if (textProfilOpcijeLozinkaTrenutna.getError() != null ||
                    textProfilOpcijeLozinkaNew.getError() != null ||
                    textProfilOpcijeLozinkaNewPonovo.getError() != null) {
                    Snackbar.make(getView(), "Molimo provjerite unesene podatke!", Snackbar.LENGTH_SHORT).show();

                    if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
                        view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.INVISIBLE);
                    }

                    return;
                }

                AuthRegister userPostObj = new AuthRegister(korisnik);
                userPostObj.setPassword(textProfilOpcijeLozinkaNew.getText().toString());

                MyApiRequest.post(getActivity(), MyApiRequest.ENDPOINT_USER_UPDATE_AUTH, userPostObj, new MyAbstractRunnable<KorisnikVM>() {
                    @Override
                    public void run(KorisnikVM korisnikVM) {
                        Log.i("Test", "run, result: " + korisnikVM.getAdresa());
                        updatePassword(korisnikVM, null, null);
                    }

                    @Override
                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                        Log.i("Test", "run, result: " + statusCode + errorMessage);
                        updatePassword(null, statusCode, errorMessage);
                    }
                });
            }
        });

        return view;
    }

    private void updatePassword(@Nullable KorisnikVM korisnik, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
            view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.INVISIBLE);
        }

        if (korisnik == null) {
            Snackbar.make(getView(), "Dogodila s greška, provjerite podatke i pokušajte ponovo." , Snackbar.LENGTH_SHORT).show();
        } else {
            MySession.setKorisnik(korisnik);
            Snackbar.make(getView(), "Uspješno ste promijenili lozinku!" , Snackbar.LENGTH_SHORT).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    getDialog().dismiss();
                }
            }).show();
        }
    }
}
