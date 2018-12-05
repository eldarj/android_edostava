package com.eldar.fit.seminarski.fragments;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;

import com.eldar.fit.seminarski.LoginActivity;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthRegister;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MyApp;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

import java.util.List;

public class ProfilOpcijeFragment extends Fragment {

    public static String Tag = "profilOpcijeFragment";

    private Button btnProfilSettingsQuestion;
    private Button btnProfilSettingsAdresa;
    private Button btnProfilSettingsSlika;
    private Button btnProfilSettingsLozinka;
    private Button btnProfilSettingsDelete;
    private KorisnikVM korisnik;
    private View view;

    public static ProfilOpcijeFragment newInstance() {
        ProfilOpcijeFragment fragment = new ProfilOpcijeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profil_opcije_fragment, container, false);

        korisnik = MySession.getKorisnik();

        ImageButton btnProfilSettingsClose = view.findViewById(R.id.btnProfilSettingsClose);
        btnProfilSettingsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        btnProfilSettingsQuestion = view.findViewById(R.id.btnProfilSettingsQuestion);
        btnProfilSettingsQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder((AppCompatActivity)getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                dlgBuilder.setTitle(R.string.fake_licence_terms)
                        .setMessage(R.string.fake_licence_terms_text)
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
                MyFragmentHelper.dodajDialog((AppCompatActivity)getActivity(),
                        ProfilPromijeniAdresuDialogFragment.Tag,
                        ProfilPromijeniAdresuDialogFragment.newInstance());
            }
        });

        btnProfilSettingsSlika = view.findViewById(R.id.btnProfilSettingsSlika);
        btnProfilSettingsSlika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragmentHelper.dodajDialog((AppCompatActivity)getActivity(),
                        ProfilPromijeniAdresuDialogFragment.Tag,
                        ProfilPromijeniSlikuDialogFragment.newInstance());
            }
        });

        btnProfilSettingsLozinka = view.findViewById(R.id.btnProfilSettingsLozinka);
        btnProfilSettingsLozinka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragmentHelper.dodajDialog((AppCompatActivity)getActivity(),
                        ProfilPromijeniAdresuDialogFragment.Tag,
                        ProfilPromijeniLozinkuDialogFragment.newInstance());
            }
        });

        btnProfilSettingsDelete = view.findViewById(R.id.btnProfilSettingsDelete);
        btnProfilSettingsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder((AppCompatActivity)getActivity(), R.style.Theme_MaterialComponents_Light_Dialog_Alert);
                dlgBuilder.setTitle(R.string.profil_delete_account)
                        .setMessage(getString(R.string.profil_delete_account_confirm) +
                                getString(R.string.profil_delete_account_desc))
                        .setPositiveButton(R.string.profil_delete_account_confirm_btn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AuthRegister userPostObj = new AuthRegister();
                                userPostObj.setId(korisnik.getId());

                                MyApiRequest.post(MyApiRequest.ENDPOINT_USER_DELETE_AUTH, userPostObj, new MyAbstractRunnable<Object>() {
                                    @Override
                                    public void run(Object o) {
                                        onAccountDeleted(dialog,204, null);
                                    }

                                    @Override
                                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                                        onAccountDeleted(dialog, statusCode, errorMessage);
                                    }
                                });
                            }
                        })
                        .setNegativeButton(getString(R.string.profil_confirm_delete_cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        return view;
    }

    private void onAccountDeleted(@Nullable DialogInterface dialog, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (view.findViewById(R.id.progressBar_snimiPromjene) != null) {
            view.findViewById(R.id.progressBar_snimiPromjene).setVisibility(View.INVISIBLE);
        }

        if (statusCode == 204) {
            Snackbar.make(getView(), R.string.profil_delete_account_success , Snackbar.LENGTH_LONG).addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    dialog.dismiss();

                    MySession.setKorisnik(null);
                    Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }
            }).show();

        } else {
            Snackbar.make(getView(), getString(R.string.dogodila_se_greska_provjerite_i_ponovite) , Snackbar.LENGTH_LONG).show();
        }
    }
}
