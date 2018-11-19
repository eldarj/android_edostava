package com.eldar.fit.seminarski.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.PosiljkaVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.getbase.floatingactionbutton.*;

import java.util.List;
import java.util.Locale;

public class PosiljkaListFragment extends Fragment {

    private List<PosiljkaVM> podaci;

    private ListView listPosiljke;
    private AddFloatingActionButton btnDodajPosiljku;
    private BaseAdapter listPosiljkeAdapter;

    public static PosiljkaListFragment newInstance() {
        Bundle args = new Bundle();

        PosiljkaListFragment fragment = new PosiljkaListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Locale locale = new Locale("en");
        Configuration cfg = getResources().getConfiguration();
        cfg.setLocale(locale);
        getResources().getConfiguration().updateFrom(cfg);

        final View view = inflater.inflate(R.layout.fragment_posiljka_list, container, false);

        btnDodajPosiljku = view.findViewById(R.id.btnNovaPosiljka);
        listPosiljke = view.findViewById(R.id.listViewPosiljke);

        listPosiljke.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PosiljkaVM posiljka = podaci.get(i);
                do_btnIzbrisiPosiljkuLongClick(posiljka);
                return true;
            }
        });

        popuniPodatke();

        btnDodajPosiljku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnDodajPosiljkuClick();
            }
        });

        return view;
    }



    private void do_btnIzbrisiPosiljkuLongClick(final PosiljkaVM posiljka) {
        final AlertDialog.Builder dlgbuilder = new AlertDialog.Builder(getActivity());
        dlgbuilder.setTitle("Obriši pošiljku");
        dlgbuilder.setMessage("Da li želite obrisati pošiljku br. " + posiljka.brojPosiljke + "?");
        dlgbuilder.setIcon(android.R.drawable.ic_delete);

        dlgbuilder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Storage.removePosiljka(posiljka);
                listPosiljkeAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        });

        dlgbuilder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dlgbuilder.show();
    }

    private void do_btnDodajPosiljkuClick() {
        MyFragmentHelper.fragmentCreate((AppCompatActivity)getActivity(), R.id.fragmentContainer, PosiljkaAddPrimaocFragment.newInstance());
    }

    private void popuniPodatke() {
        podaci = Storage.getPosiljke();

        listPosiljkeAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                if (view == null) {
                    //LayoutInflater inflater = getLayoutInflater();
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater != null ? inflater.inflate(R.layout.stavka_posiljka, viewGroup, false) : null;
                }

                TextView title = view.findViewById(R.id.stavka_posiljke_title);
                TextView subtitle = view.findViewById(R.id.stavka_posiljke_subtitle);
                TextView date = view.findViewById(R.id.stavka_posiljke_super);
                TextView meta = view.findViewById(R.id.stavka_posiljka_meta);

                date.setText(podaci.get(i).getFormattedDatum(""));
                title.setText(getString(R.string.stavka_posiljke_title,
                        podaci.get(i).primaoc.getIme(),
                        podaci.get(i).primaoc.getPrezime()));
                subtitle.setText(getString(R.string.stavka_posiljke_subtitle,
                        "\"" + podaci.get(i).napomena + "\"",
                        Float.toString(podaci.get(i).masa) + " kg",
                        Float.toString(podaci.get(i).iznos) + " KM"));
                meta.setText(getString(R.string.stavka_posiljke_meta_broj,
                        podaci.get(i).brojPosiljke));

                return view;
            }
        };

        listPosiljke.setAdapter(listPosiljkeAdapter);
    }
}
