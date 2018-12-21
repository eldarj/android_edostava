package com.eldar.fit.seminarski.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.RestoranDetaljnoActivity;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.RestoranPrikazVM;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.data.RestoranInfo;

import java.util.List;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;
import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN_FRAGMENT_FLAG;

public class RestoranListFragment extends Fragment {
    public static String Tag = "restoranListMyTaggedFragment";
    private static final String FILTER_BY_OMILJENI = "filterRestoraniByOmiljeni";

    private ListView listRestorani;
    private List<RestoranInfo> podaci;
    private BaseAdapter listRestoraniAdapter;

    private ProgressBar progressBar_restorani;
    private TextView textNemateOmiljenih;

    private boolean showOmiljeneOnly;
    private View listView;

    public static RestoranListFragment newInstance(boolean filterByOmiljeni) {
        RestoranListFragment fragment = new RestoranListFragment();

        Bundle args = new Bundle();
        args.putBoolean(FILTER_BY_OMILJENI, filterByOmiljeni);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(FILTER_BY_OMILJENI)) {
            showOmiljeneOnly = getArguments().getBoolean(FILTER_BY_OMILJENI);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listView = inflater.inflate(R.layout.restoran_list_fragment, container, false);

        progressBar_restorani = listView.findViewById(R.id.progressBar_restoraniList);
        textNemateOmiljenih = listView.findViewById(R.id.textNemateOmiljenih);

        listRestorani = listView.findViewById(R.id.listViewRestorani);

        restoraniListRequest();

        listRestorani.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                do_transitionCardView(view, podaci.get(position),  RestoranDetaljnoActivity.DETAIL_VIEW_GOTO_INFO);
            }
        });

        return listView;
    }

    private void restoraniListRequest() {
        MyApiRequest.get(MyApiRequest.ENDPOINT_RESTORANI, new MyAbstractRunnable<RestoranPrikazVM>() {
            @Override
            public void run(RestoranPrikazVM restoranPrikazVM) {
                popuniPodatke(restoranPrikazVM);
                progressBar_restorani.setVisibility(View.INVISIBLE);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                Snackbar.make(getActivity().findViewById(R.id.fragmentRestoranListContainer),
                        "Dogodila se greška, molimo pokušajte ponovo.",
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.api_load_ponovo), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                restoraniListRequest();
                            }
                        })
                        .show();
            }
        });
    }

    private void do_transitionCardView(View view, RestoranInfo restoran, String fragmentFlag) {
         Intent intent = new Intent(getActivity(), RestoranDetaljnoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(DETAIL_VIEW_RESTORAN, restoran);
            bundle.putSerializable(DETAIL_VIEW_RESTORAN_FRAGMENT_FLAG, fragmentFlag);
            intent.putExtras(bundle);
        try {
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(getActivity(), view, getString(R.string.transition_restoran_card));

            startActivity(intent, options.toBundle());
        } catch (Exception e) { // ako dođe do transition greške, učitaj activity normalno...
            startActivity(intent);
            //e.printStackTrace();
        }
    }

    private void popuniPodatke(RestoranPrikazVM model) {
        if (showOmiljeneOnly) {
            podaci = model.getOmiljeniRestorani();
        } else {
            podaci = model.getRestorani();
        }

        if (podaci == null || podaci.size() == 0) {
            textNemateOmiljenih.setVisibility(View.VISIBLE);
        }

        listRestoraniAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return podaci.size();
            }

            @Override
            public Object getItem(int position) { return podaci.get(position); }

            @Override
            public long getItemId(int position) {
                return podaci.get(position).getId();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {
                if (view == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    view = inflater.inflate(R.layout.restoran_stavka, parent, false);
                }

                RestoranInfo restoran = podaci.get(position);

                ProgressBar progressBar_restoranLike = view.findViewById(R.id.progressBar_restoranLike);

                ImageButton btnStavkaRestoranLike = view.findViewById(R.id.btnStavkaRestoranLike);
                btnStavkaRestoranLike.setImageResource(restoran.userHasLiked() ?
                                R.drawable.ic_heart_red : R.drawable.ic_heart);

                btnStavkaRestoranLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar_restoranLike.setVisibility(View.VISIBLE);
                        btnStavkaRestoranLike.setVisibility(View.INVISIBLE);
                        if (restoran.userHasLiked()) {
                            MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_RESTORANI_UNLIKE, restoran.getId()),
                                new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                                new MyAbstractRunnable<String>() {
                                    @Override
                                    public void run(String response) {
                                        onRestoranLikeDislike(response,
                                                null,
                                                null,
                                                progressBar_restoranLike,
                                                btnStavkaRestoranLike,
                                                restoran);
                                    }

                                    @Override
                                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                                        onRestoranLikeDislike(null,
                                                statusCode, errorMessage,
                                                progressBar_restoranLike,
                                                null,
                                                null);
                                    }
                                });
                        } else {
                            MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_RESTORANI_LIKE, restoran.getId()),
                                new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                                new MyAbstractRunnable<String>() {
                                    @Override
                                    public void run(String response) {
                                        onRestoranLikeDislike(response, null, null, progressBar_restoranLike, btnStavkaRestoranLike, restoran);
                                    }

                                    @Override
                                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                                        onRestoranLikeDislike(null, statusCode, errorMessage, progressBar_restoranLike, null, null);
                                    }
                            });
                        }

                    }
                });

                TextView restoranNaziv = view.findViewById(R.id.textStavkaRestoranNaziv);
                TextView restoranOpis = view.findViewById(R.id.textStavkaRestoranOpis);
                TextView restoranStatsCount = view.findViewById(R.id.textStavkaRestoranStats);
                TextView textStavkaRestoranLokacija = view.findViewById(R.id.textStavkaRestoranLokacija);
                ImageView restoranSlikaView = view.findViewById(R.id.imageStavkaRestoranSlika);

                MaterialButton btnStavkaRestoranJelovnik = view.findViewById(R.id.btnStavkaRestoranJelovnik);
                btnStavkaRestoranJelovnik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        do_transitionCardView(listView, restoran, RestoranDetaljnoActivity.DETAIL_VIEW_GOTO_JELOVNIK);
                    }
                });

                restoranNaziv.setText(restoran.getNaziv());

                int limit = 120;
                String opis;
                if (restoran.getOpis().length() > limit ) {
                    opis = restoran.getOpis().substring(0, limit - 3) + "...";
                } else {
                    opis = restoran.getOpis();
                }
                restoranOpis.setText(opis);

                restoranStatsCount.setText(getString(R.string.restoran_stats, restoran.getLikesCount(), restoran.getRecenzije().size()));

                textStavkaRestoranLokacija.setText(restoran.getLokacija());

                if (restoran.getSlika() != null) {
                    Glide.with(getActivity())
                            .load(restoran.getSlika())
                            .centerCrop()
                            .into(restoranSlikaView);
                }

                return view;
            }

        };

        listRestorani.setAdapter(listRestoraniAdapter);
    }

    private void onRestoranLikeDislike(@Nullable String response,
       @Nullable Integer statusCode,
       @Nullable String errorMessage,
       @Nullable ProgressBar progressBar,
       ImageButton btnStavkaRestoranLike,
       RestoranInfo restoran
    ) {
        try {
            progressBar.setVisibility(View.INVISIBLE);
            btnStavkaRestoranLike.setVisibility(View.VISIBLE);
            if (response != null) {
                btnStavkaRestoranLike.setImageResource(restoran.toggleLike() ?
                        R.drawable.ic_heart_red : R.drawable.ic_heart);

                Snackbar.make(getActivity().findViewById(R.id.fragmentRestoranListContainer),
                        response,
                        Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(getActivity().findViewById(R.id.fragmentRestoranListContainer),
                        errorMessage != null ? errorMessage : getString(R.string.dogodila_se_greska),
                        Snackbar.LENGTH_LONG).show();
            }
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
    }
}
