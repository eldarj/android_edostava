package com.eldar.fit.seminarski.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.PosiljkaVM;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MyFragmentHelper.*;
import com.getbase.floatingactionbutton.*;

public class PosiljkaAddPrimaocFragment extends Fragment {

    private PosiljkaVM posiljkaVM;
    public static String BUNDLE_INPUT_IME_PRIMAOCA = "input_ime_primaoca";
    public static String BUNDLE_INPUT_ADRESA_PRIMAOCA = "input_adresa_primaoca";

    TextView inputImePrimaoca, inputAdresaPrimaoca;
    FloatingActionsMenu btnMenuPrimaoc;
    FloatingActionButton btnPromijeniPrimaoca, btnDalje;

    public static PosiljkaAddPrimaocFragment newInstance() {
        Bundle args = new Bundle();
        PosiljkaAddPrimaocFragment fragment = new PosiljkaAddPrimaocFragment();
        fragment.setArguments(args);

        return fragment;
    }


    /*
     * FIND: "FIRST APPROACH: USING INTERFACE LISTENERS"
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posiljka_dodaj_primaoc, container, false);

        btnMenuPrimaoc = view.findViewById(R.id.btnMenuPrimaoc);

        inputImePrimaoca = view.findViewById(R.id.inputImePrimaoca);
            inputImePrimaoca.setOnClickListener(handler_IzaberitePrimaoca());
            inputImePrimaoca.setKeyListener(null); // Set to readonly

        inputAdresaPrimaoca = view.findViewById(R.id.inputAdresaPrimaoca);
            inputAdresaPrimaoca.setOnClickListener(handler_IzaberitePrimaoca());
            inputAdresaPrimaoca.setKeyListener(null); // Set to readonly

        btnDalje = view.findViewById(R.id.btnPosaljiDalje);
        btnPromijeniPrimaoca = view.findViewById(R.id.btnPromijeniPrimaoca);

        if (getArguments().containsKey(BUNDLE_INPUT_IME_PRIMAOCA) ) {
            inputImePrimaoca.setText(getArguments().getString(BUNDLE_INPUT_IME_PRIMAOCA));
        }
        if (getArguments().containsKey(BUNDLE_INPUT_ADRESA_PRIMAOCA)) {
            inputAdresaPrimaoca.setText(getArguments().getString(BUNDLE_INPUT_ADRESA_PRIMAOCA));
        }

        btnPromijeniPrimaoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnPromijeniPrimaocaClick();
            }
        });

        btnDalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnDaljeClick();
            }
        });

        return view;
    }

    private View.OnClickListener handler_IzaberitePrimaoca() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),
                        "Ne možete ručno mijenjati ime, prezime i adresu primaoca. Odaberite postojećeg s liste primaoca!",
                        Toast.LENGTH_SHORT)
                        .show();
                btnMenuPrimaoc.expand();
            }
        };
    }

    private void do_btnDaljeClick() {
        if (posiljkaVM != null) {
            MyFragmentHelper.fragmentCreate(getActivity(), R.id.fragmentContainer, PosiljkaAddPaketFragment.newInstance(posiljkaVM));
        }
    }

    private void do_btnPromijeniPrimaocaClick() {

        RunnableCallback<KorisnikVM> callback = new RunnableCallback<KorisnikVM>() {
            @Override
            public void run(KorisnikVM result) {
                posiljkaVM = new PosiljkaVM();
                posiljkaVM.primaoc = result;
                inputImePrimaoca.setText(getString(R.string.posiljka_dodaj_primaoc_ime,
                        result.getIme(),
                        result.getPrezime()));

                inputAdresaPrimaoca.setText(getString(R.string.posiljka_dodaj_primaoc_adresa,
                        result.getOpstinaVM().getnaziv(),
                        result.getOpstinaVM().getdrzava()));
            }
        };

        KorisnikPretragaDialogFragment dlg = KorisnikPretragaDialogFragment.newInstance(callback);
        MyFragmentHelper.dodajDialog(getActivity(), "promijeniPrimaocaDialog", dlg);
    }

}
