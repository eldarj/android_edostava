package com.eldar.fit.seminarski.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;

public class ProfilSettingsFragment extends Fragment {

    private Button btnProfilSettingsQuestion;
    private Button btnProfilSettingsImePrezime;
    private Button btnProfilSettingsAdresa;
    private Button btnProfilSettingsSlika;
    private Button btnProfilSettingsLozinka;
    private Button btnProfilSettingsDelete;

    public static ProfilSettingsFragment newInstance() {
        ProfilSettingsFragment fragment = new ProfilSettingsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_settings, container, false);

        btnProfilSettingsQuestion = view.findViewById(R.id.btnProfilSettingsQuestion);
        btnProfilSettingsQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder((AppCompatActivity)getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                dlgBuilder.setTitle("Licencni ugovor")
                        .setMessage("Korisnik alikacije eDostava! je dužan isporučiti ispravne korisničke podatke i adresu. " +
                                "\n\nKorisnik pri kreiranju narudžbe garantuje dostavljaču da će preuzeti narudžbu u naznačenom vremenu i platiti naznačeni iznos.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        btnProfilSettingsAdresa = view.findViewById(R.id.btnProfilSettingsAdresa);
        btnProfilSettingsAdresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragmentHelper.dodajDialog((AppCompatActivity)getActivity(), "dlgPromijeniAdresu", ProfilPromijeniAdresuDialogFragment.newInstance());
            }
        });

        btnProfilSettingsSlika = view.findViewById(R.id.btnProfilSettingsSlika);
        btnProfilSettingsLozinka = view.findViewById(R.id.btnProfilSettingsLozinka);

        btnProfilSettingsDelete = view.findViewById(R.id.btnProfilSettingsDelete);
        btnProfilSettingsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder((AppCompatActivity)getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                dlgBuilder.setTitle("Izbriši korisnički račun")
                        .setMessage("Da li ste sigurni da želite izbrisati korisnički račun?" +
                                "\n\nTrenutne sačuvane preference i korisnički podaci će biti trajno uklonjeni sa sistema, te nećete moći više koristiti aplikaciju eDostava!")
                        .setPositiveButton("Da, izbriši račun", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Snackbar.make(getView(), "Uspješno ste izbrisali korisnički račun!", Snackbar.LENGTH_LONG).show();
                            }
                        })
                        .show();
            }
        });


        return view;
    }
}
