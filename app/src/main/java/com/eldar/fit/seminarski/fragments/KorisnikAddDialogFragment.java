package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.BlokVM;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.OpstinaVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MyUtils;

import java.util.List;

public class KorisnikAddDialogFragment extends DialogFragment {

    ImageButton btnClose;
    Button btnSnimi;
    TextInputEditText inputIme, inputPrezime;
    Spinner spinnerOpstina;

    private static MyFragmentHelper.RunnableCallback callback;
    private static String BUNDLE_KORISNIK_ADD_CALLBACK = "bundle_korisnik_add_callback";

    public static KorisnikAddDialogFragment newInstance(MyFragmentHelper.RunnableCallback callback) {
        KorisnikAddDialogFragment korisnik = new KorisnikAddDialogFragment();

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_KORISNIK_ADD_CALLBACK, callback);
        korisnik.setArguments(args);

        return korisnik;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(BUNDLE_KORISNIK_ADD_CALLBACK)) {
            callback = (MyFragmentHelper.RunnableCallback) getArguments().getSerializable(BUNDLE_KORISNIK_ADD_CALLBACK);
        }
        setStyle(STYLE_NORMAL, R.style.DialogsTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_korisnik_dodaj, container, false);

        inputIme = view.findViewById(R.id.inputKorisnikIme);
        inputPrezime = view.findViewById(R.id.inputKorisnikPrezime);

        btnClose = view.findViewById(R.id.btnKorisnikNoviClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        btnSnimi = view.findViewById(R.id.btnKorisnikNoviSnimi);
        btnSnimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnKorisnikSnimiClick();
            }
        });

        spinnerOpstina = view.findViewById(R.id.spinnerKorisnikOpstina);
        List<String> opstinaPodaci = Storage.getStringListOpstine();
        MyUtils.popuniSpinner(getActivity(),
                opstinaPodaci,
                android.R.layout.simple_spinner_item,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerOpstina);

        return view;
    }

    private void do_btnKorisnikSnimiClick() {
        int opstinaPosition = spinnerOpstina.getSelectedItemPosition();
        OpstinaVM izabranaOpstina = Storage.getOpstinaByPosition(opstinaPosition);
        izabranaOpstina = null;
        BlokVM izabraniBlok = Storage.getBlokovi().get(0);

        KorisnikVM noviKorisnik = new KorisnikVM("", "", inputIme.getText().toString(), inputPrezime.getText().toString(), izabraniBlok);
        Storage.addKorisnik(noviKorisnik);

        callback.run(noviKorisnik);
        btnClose.callOnClick();
    }

}
