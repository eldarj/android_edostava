package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.PosiljkaVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;

public class PosiljkaAddPaketFragment extends Fragment {

    public static String BUNDLE_NOVA_POSILJKA = "bundle_nova_posiljka";
    public PosiljkaVM posiljkaVM;

    private EditText inputMasa, inputNapomena, inputIznos;
    private Switch switchPouzece;
    private Button btnDalje;

    public static Fragment newInstance(PosiljkaVM posiljkaVM) {
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_NOVA_POSILJKA, posiljkaVM);

        PosiljkaAddPaketFragment fragment = new PosiljkaAddPaketFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posiljka_dodaj_paket, container, false);
        posiljkaVM = (PosiljkaVM) getArguments().getSerializable(BUNDLE_NOVA_POSILJKA);

        inputMasa = (EditText) view.findViewById(R.id.inputMasa);
        inputNapomena = (EditText) view.findViewById(R.id.inputNapomena);
        inputIznos = (EditText) view.findViewById(R.id.inputIznos);
        switchPouzece = (Switch) view.findViewById(R.id.switchPouzecem);
        btnDalje = (Button) view.findViewById(R.id.btnPaketDalje);

        btnDalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_dodajPosiljku();
            }
        });

        return view;
    }

    private void do_dodajPosiljku() {
        try {
            posiljkaVM = new PosiljkaVM(posiljkaVM.primaoc,
                    Float.parseFloat(inputMasa.getText().toString()),
                    Float.parseFloat(inputIznos.getText().toString()),
                    inputNapomena.getText().toString());

            Storage.addPosiljka(posiljkaVM);
            MyFragmentHelper.fragmentCreate((AppCompatActivity)getActivity(), R.id.fragmentContainer, PosiljkaListFragment.newInstance());
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
