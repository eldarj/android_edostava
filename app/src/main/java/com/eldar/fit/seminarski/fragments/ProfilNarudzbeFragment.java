package com.eldar.fit.seminarski.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.NarudzbaPrikazVM;
import com.eldar.fit.seminarski.data.NarudzbaStavkaVM;
import com.eldar.fit.seminarski.data.NarudzbaVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ProfilNarudzbeFragment extends Fragment {

    private static final String LIST_NARUDZBE_QUERY = "profilListNarudzbeQuery";
    public static String Tag = "profilNarudzbeFragment";

    private NarudzbaVM narudzbaIzbrisi;

    private SearchView searchViewNarudzbe;
    private ImageButton btnNarudzbeClose;
    private ListView listViewNarudzbe;


    private List<NarudzbaVM> podaci;
    private List<NarudzbaVM> initialPodaci;
    private BaseAdapter listNarudzbeAdapter;
    private TextView profilnarudzbeNoData;
    private ProgressBar progressBar_narudzbaList;
    private String predefinedQuery = "";

    public static ProfilNarudzbeFragment newInstance(String query) {
        ProfilNarudzbeFragment fragment = new ProfilNarudzbeFragment();

        Bundle args = new Bundle();
        args.putString(LIST_NARUDZBE_QUERY, query);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(LIST_NARUDZBE_QUERY)) {
            predefinedQuery = getArguments().getString(LIST_NARUDZBE_QUERY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_narudzbe_fragment, container, false);

        progressBar_narudzbaList = view.findViewById(R.id.progressBar_narudzbaList);
        profilnarudzbeNoData = view.findViewById(R.id.profilnarudzbeNoData);

        btnNarudzbeClose = view.findViewById(R.id.btnNarudzbeClose);
        btnNarudzbeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        searchViewNarudzbe = view.findViewById(R.id.searchViewNarudzbe);
        searchViewNarudzbe.setQuery(predefinedQuery, false);
        searchViewNarudzbe.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateNarudzbaList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateNarudzbaList(newText);
                return true;
            }
        });

        listViewNarudzbe = view.findViewById(R.id.listViewNarudzbe);
        AuthLogin credentialsObj = new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword());
        MyApiRequest.post(MyApiRequest.ENDPOINT_NARUDZBE, credentialsObj, new MyAbstractRunnable<NarudzbaPrikazVM>() {
            @Override
            public void run(NarudzbaPrikazVM narudzbaPrikazVM) {
                onNarudzbaListReceived(narudzbaPrikazVM, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                onNarudzbaListReceived(null, statusCode, errorMessage);
            }
        });

        listViewNarudzbe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                progressBar_narudzbaList.setVisibility(View.VISIBLE);

                narudzbaIzbrisi = podaci.get(position);
                try {
                    MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_NARUDZBE_STATUS, narudzbaIzbrisi.getId()),
                            new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                            new MyAbstractRunnable<String>() {
                                @Override
                                public void run(String status) {
                                    onCanBeDeleted(status, null, null);
                                }
                                @Override
                                public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                                    onCanBeDeleted(null, statusCode, errorMessage);
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        return view;
    }

    private void onCanBeDeleted(@Nullable String statusNarudzbe, @Nullable Integer statusCode, @Nullable String errorMessage) {
        progressBar_narudzbaList.setVisibility(View.INVISIBLE);

        if (statusNarudzbe != null && Objects.equals(statusNarudzbe, "Na čekanju")) {
            MyFragmentHelper.RunnableCallback<NarudzbaVM> callback = new MyFragmentHelper.RunnableCallback<NarudzbaVM>() {
                @Override
                public void run(NarudzbaVM izbrisanaNarudzba) {
                    initialPodaci.remove(izbrisanaNarudzba);
                    updateNarudzbaList(searchViewNarudzbe.getQuery().toString());
                }
            };

            MyFragmentHelper.dodajDialog((AppCompatActivity)getActivity(),
                    IzbrisiNarudzbuDialogFragment.Tag,
                    IzbrisiNarudzbuDialogFragment.newInstance(narudzbaIzbrisi, callback));
        } else {
            Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                    errorMessage != null ? errorMessage : getString(R.string.dogodila_se_greska_narudzba_prihvacena),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void onNarudzbaListReceived(@Nullable NarudzbaPrikazVM narudzbePodaci, @Nullable Integer statusCode, @Nullable String errorMessage) {
        progressBar_narudzbaList.setVisibility(View.INVISIBLE);

        int noDataTextVisibility = narudzbePodaci != null &&
                    narudzbePodaci.narudzbe != null &&
                    narudzbePodaci.narudzbe.size() > 0
                ? View.INVISIBLE: View.VISIBLE;
        profilnarudzbeNoData.setVisibility(noDataTextVisibility);

        if (narudzbePodaci != null) {
            initialPodaci = narudzbePodaci.narudzbe;
            if (predefinedQuery != null && predefinedQuery.length() > 0 ) {
                podaci= getFiltered(predefinedQuery);
            } else {
                podaci = initialPodaci;
            }
            popuniPodatke();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.content),
                    getString(R.string.dogodila_se_greska),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void updateNarudzbaList(String query) {
        podaci = getFiltered(query);
        listNarudzbeAdapter.notifyDataSetChanged();
    }

    private List<NarudzbaVM> getFiltered(String query) {
        List<NarudzbaVM> filtered = new ArrayList<NarudzbaVM>();

        for (NarudzbaVM n: initialPodaci) {
            if (n.getSearchIndex().contains(query.toLowerCase())) {
                filtered.add(n);
            }
        }
        return filtered;
    }

    private void popuniPodatke() {
        listNarudzbeAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater != null ? inflater.inflate(R.layout.profil_narudzba_stavka, parent, false) : null;
                }
                NarudzbaVM n = podaci.get(position);

                TextView textStavkaNarudzbaSifra = view.findViewById(R.id.textStavkaNarudzbaSifra);
                textStavkaNarudzbaSifra.setText(n.getuId().toString());

                TextView textStavkaNarudzbaDatum = view.findViewById(R.id.textStavkaNarudzbaDatum);
                textStavkaNarudzbaDatum.setText("Datum kreiranja " + n.getDatumNapravljenaString());

                TextView textStavkaNarudzbaCijena = view.findViewById(R.id.textStavkaNarudzbaCijena);
                textStavkaNarudzbaCijena.setText(getString(R.string.cijena_double, n.getUkupnaCijena()));

                TextView textStavkaNarudzbaStatus = view.findViewById(R.id.textStavkaNarudzbaStatus);
                textStavkaNarudzbaStatus.setText(n.getStatus());

                try {
                    Set<String> restorani = new HashSet<String>(n.getNarucenoIzRestorana());
                    ChipGroup chipGroupRestorani = view.findViewById(R.id.chipgroupStavkaNarudzbaRestorani);
                    chipGroupRestorani.removeAllViews();
                    chipGroupRestorani.setFocusable(false);
                    for (String restoranNaziv :
                            restorani) {
                        Chip c = new Chip(getActivity());
                        c.setText(restoranNaziv);
                        c.setFocusable(false);
                        c.setClickable(false);
                        chipGroupRestorani.addView(c);
                    }

                    Set<NarudzbaStavkaVM> hrana = new HashSet<NarudzbaStavkaVM>(n.getNarudzbaStavke());
                    ChipGroup chipGroupHrana = view.findViewById(R.id.chipgroupStavkaNarudzbaHrana);
                    chipGroupHrana.removeAllViews();
                    chipGroupHrana.setFocusable(false);
                    for (NarudzbaStavkaVM stavka :
                            hrana) {
                        Chip c = new Chip(getActivity());
                        c.setText("x" + stavka.getKolicina() + " " + stavka.getNaziv());
                        c.setFocusable(false);
                        c.setClickable(false);
                        chipGroupHrana.addView(c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return view;
            }
        };

        listViewNarudzbe.setAdapter(listNarudzbeAdapter);
    }
}
