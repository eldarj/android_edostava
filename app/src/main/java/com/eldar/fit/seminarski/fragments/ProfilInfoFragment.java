package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MySession;

public class ProfilInfoFragment extends Fragment {


    private ImageView imageProfil;
    private TextView textProfilImePrezimeHeader;
    private TextView textProfilImePrezime;
    private TextView textProfilAdresa;
    private TextView textProfilUsername;
    private TextView textProfilDatumRegistracije;
    private TextView chipProfilUkupnoNarudzbi;
    private TextView chipProfilOmiljeniRestoran;
    private Button btnProfilMojeNarudzbe;

    public static ProfilInfoFragment newInstance() {
        ProfilInfoFragment fragment = new ProfilInfoFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_info, container, false);

        KorisnikVM korisnik = MySession.getKorisnik();

        textProfilImePrezimeHeader = view.findViewById(R.id.textProfilImePrezimeHeader);
        textProfilImePrezimeHeader.setText(korisnik.getIme() + " " + korisnik.getPrezime());

        imageProfil = view.findViewById(R.id.imageProfilHeader);
        Glide.with(getActivity())
                .load("https://i.pinimg.com/originals/f6/77/b6/f677b6463a402c2d5e8ca609d1cc2b03.jpg")
                .centerCrop()
                .into(imageProfil);

        textProfilImePrezime = view.findViewById(R.id.textProfilImePrezime);
        textProfilImePrezime.setText(korisnik.getIme() + " " + korisnik.getPrezime());

        textProfilAdresa = view.findViewById(R.id.textProfilAdresa);
        textProfilAdresa.setText(korisnik.getAdresa() + ", " + korisnik.getBlokVM().getNaziv() + " " + korisnik.getBlokVM().getOpstina());

        textProfilUsername = view.findViewById(R.id.textProfilUsername);
        textProfilUsername.setText(korisnik.getUsername());

        textProfilDatumRegistracije = view.findViewById(R.id.textProfilDatumRegistracije);
        textProfilDatumRegistracije.setText(korisnik.getDatumRegistracije());

        chipProfilUkupnoNarudzbi = view.findViewById(R.id.chipProfilUkupnoNarudzbi);
        chipProfilUkupnoNarudzbi.setText(korisnik.getUkupnoNarudzbi());

        chipProfilOmiljeniRestoran = view.findViewById(R.id.chipProfilOmiljeniRestoran);
        chipProfilOmiljeniRestoran.setText(korisnik.getOmiljeniRestoran());

        btnProfilMojeNarudzbe = view.findViewById(R.id.btnProfilMojeNarudzbe);
        btnProfilMojeNarudzbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Test", "moje narudzbe clicked!");
            }
        });

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
        Log.i("Test", "Selected " + item.getItemId());
        switch(item.getItemId()){
            case R.id.actionProfilPromijeniLozinku:
                break;
            case R.id.actionProfilPromijeniSliku:
                break;
            case R.id.actionProfilPromijeniOsnovnePodatke:
                break;
            default:
                return false;
        }
        return true;
    }
}
