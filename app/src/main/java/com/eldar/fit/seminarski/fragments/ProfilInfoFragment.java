package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;

public class ProfilInfoFragment extends Fragment {

    public static String Tag = "profilInfoFragment";

    private ImageView imageProfil;
    private TextView textProfilImePrezimeHeader;
    private TextView textProfilImePrezime;
    private TextView textProfilAdresa;
    private TextView textProfilUsername;
    private TextView textProfilDatumRegistracije;
    private TextView chipProfilUkupnoNarudzbi;
    private TextView chipProfilZadnjiLogin;
    private Button btnProfilMojeNarudzbe;
    private Button btnProfilClose;
    private Toolbar myToolbar;
    private ImageView imageProfilFullScreen;
    private LinearLayout imageProfilFullscreenHolder;


    public static ProfilInfoFragment newInstance() {
        ProfilInfoFragment fragment = new ProfilInfoFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_info_fragment, container, false);

        KorisnikVM korisnik = MySession.getKorisnik();

        textProfilImePrezimeHeader = view.findViewById(R.id.textProfilImePrezimeHeader);
        textProfilImePrezimeHeader.setText(korisnik.getIme() + " " + korisnik.getPrezime());

        imageProfil = view.findViewById(R.id.imageProfilHeader);
        Glide.with(getActivity())
                .load(korisnik.getImageUrlForDisplay())
                .centerCrop()
                .into(imageProfil);

        textProfilImePrezime = view.findViewById(R.id.textProfilImePrezime);
        textProfilImePrezime.setText(korisnik.getIme() + " " + korisnik.getPrezime());

        textProfilAdresa = view.findViewById(R.id.textProfilAdresa);
        textProfilAdresa.setText(korisnik.getAdresa() + ", " + korisnik.getLokacija());

        textProfilUsername = view.findViewById(R.id.textProfilUsername);
        textProfilUsername.setText("Username " + korisnik.getUsername());

        textProfilDatumRegistracije = view.findViewById(R.id.textProfilDatumRegistracije);
        textProfilDatumRegistracije.setText("Datum registracije " + korisnik.getDatumRegistracije());

        chipProfilUkupnoNarudzbi = view.findViewById(R.id.chipProfilUkupnoNarudzbi);
        chipProfilUkupnoNarudzbi.setText("Ukupno narudžbi " + korisnik.getNarudzbeCount());

        chipProfilZadnjiLogin = view.findViewById(R.id.chipProfilZadnjiLogin);
        chipProfilZadnjiLogin.setText("Zadnji put logiran  " + korisnik.getZadnjiLogin());

        btnProfilMojeNarudzbe = view.findViewById(R.id.btnProfilMojeNarudzbe);
        btnProfilMojeNarudzbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFragmentHelper.fragmentReplace((AppCompatActivity)getActivity(),
                        R.id.fragmentProfilContainer,
                        ProfilNarudzbeFragment.newInstance(),
                        ProfilNarudzbeFragment.Tag,
                        true);
            }
        });

        ImageButton btnProfilClose = view.findViewById(R.id.btnProfilClose);
        btnProfilClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        myToolbar = (Toolbar) view.findViewById(R.id.toolbarProfil);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.nav_profil_secondary, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.actionUrediProfil:
                MyFragmentHelper.fragmentReplace((AppCompatActivity)getActivity(),
                        R.id.fragmentProfilContainer,
                        ProfilOpcijeFragment.newInstance(),
                        ProfilOpcijeFragment.Tag,
                        true);
                break;
            default:
                return false;
        }
        return true;
    }
}
