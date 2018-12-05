package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.ApiSubscriberRestoranKomentari;
import com.eldar.fit.seminarski.data.NewKomentarRequest;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;
import com.eldar.fit.seminarski.helper.RestoranInfo;
import com.eldar.fit.seminarski.helper.RestoranRecenzija;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RestoranKomentariPagerFragment extends Fragment {
    private static final String DETALJNO_PAGER_RESTORAN_OBJ = "detaljnoPagerRestoranObj";
    public static final String PAGE_TITLE = "Komentari";

    private ListView listViewRecenzije;
    private List<RestoranRecenzija> komentari;
    private BaseAdapter listKomentariAdapter;
    private RestoranInfo restoran;
    private TextInputEditText inputRestoranKomentarText;
    private Button btnRestoranKomentarNapisi;
    private TextView komentariNoData;
    private ProgressBar progressBar_komentari;
    private LinearLayout komentariRoot;

    public static RestoranKomentariPagerFragment newInstance(RestoranInfo restoranInfo) {
        RestoranKomentariPagerFragment fragment = new RestoranKomentariPagerFragment();

        Bundle args = new Bundle();
        args.putSerializable(DETALJNO_PAGER_RESTORAN_OBJ, restoranInfo);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(DETALJNO_PAGER_RESTORAN_OBJ)) {
            restoran = (RestoranInfo) getArguments().getSerializable(DETALJNO_PAGER_RESTORAN_OBJ);
            komentari = restoran.getRecenzije();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restoran_komentari_pager_fragment, container, false);

        progressBar_komentari = view.findViewById(R.id.progressBar_komentariList);
        komentariNoData = view.findViewById(R.id.komentariNoData);
        komentariNoData.setVisibility(komentari != null && komentari.size() > 0 ? View.INVISIBLE: View.VISIBLE);

        inputRestoranKomentarText = view.findViewById(R.id.inputRestoranKomentarText);

        btnRestoranKomentarNapisi = view.findViewById(R.id.btnRestoranKomentarNapisi);
        btnRestoranKomentarNapisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_napisiKomentar();
            }
        });

        listViewRecenzije = view.findViewById(R.id.listViewRestoraniRecenzije);
        popuniKomentare();

        return view;
    }

    private void do_napisiKomentar() {
        String komentarText = inputRestoranKomentarText.getText().toString().trim();
        if (komentarText.length() < 5) {
            inputRestoranKomentarText.setError(getString(R.string.input_komentar_error));
            return;
        }

        MyUtils.dismissKeyboard(getActivity());
        inputRestoranKomentarText.setText("");
        inputRestoranKomentarText.clearFocus();
        progressBar_komentari.setVisibility(View.VISIBLE);
        btnRestoranKomentarNapisi.setVisibility(View.INVISIBLE);

        MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_RESTORANI_KOMENTARI_NOVI, restoran.getId()),
                new NewKomentarRequest(MySession.getAuthLogin(), komentarText),
                new MyAbstractRunnable<ApiSubscriberRestoranKomentari>() {
                    @Override
                    public void run(ApiSubscriberRestoranKomentari returnedData) {
                        onKomentarCreated(returnedData.getKomentari(), null, null);
                    }

                    @Override
                    public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                        onKomentarCreated(null, statusCode, errorMessage);
                    }
                });
    }

    private void onKomentarCreated(@Nullable List<RestoranRecenzija> podaci, @Nullable Integer statusCode, @Nullable String errorMessage) {
        progressBar_komentari.setVisibility(View.INVISIBLE);
        btnRestoranKomentarNapisi.setVisibility(View.VISIBLE);
        komentariNoData.setVisibility(podaci != null && podaci.size() > 0 ? View.INVISIBLE: View.VISIBLE);

        if (podaci != null) {
            komentari = podaci;
            listKomentariAdapter.notifyDataSetChanged();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                    getString(R.string.dogodila_se_greska) + " " + getString(R.string.dogodila_se_greska_posalji_komentar_ponovo),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void popuniKomentare() {
        listKomentariAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return komentari.size();
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
                    view = getLayoutInflater().inflate(R.layout.restoran_komentar_stavka, parent, false);
                }

                RestoranRecenzija komentar = komentari.get(position);

                CircleImageView imageRestoranKomentarUserImg = view.findViewById(R.id.imageRestoranKomentarUserImg);
                Glide.with(getActivity())
                        .load(komentar.getImageUrlForDisplay())
                        .centerCrop()
                        .into(imageRestoranKomentarUserImg);

                TextView textRestoranKomentarSuper = view.findViewById(R.id.textRestoranKomentarSuper);
                textRestoranKomentarSuper.setText(String.format("@%1$s %2$s", komentar.getUsername(), komentar.getDatumString()));

                TextView textRestoranKomentarUsername = view.findViewById(R.id.textRestoranKomentarUsername);
                textRestoranKomentarUsername.setText(komentar.getImePrezime());

                TextView textRestoranKomentar = view.findViewById(R.id.textRestoranKomentar);
                textRestoranKomentar.setText(komentar.getRecenzija());

                return view;
            }
        };
        listViewRecenzije.setAdapter(listKomentariAdapter);
    }

    /* Testiranje metoda za long-polling - konstantan request za slušanje promjene (hash-a) nad podacima
    * Možda implementiati nekad ubuduće */
    @Override
    public void onPause() {
        super.onPause();
        // do_breakKomentariSubscription();
    }

    @Override
    public void onResume() {
        super.onResume();
        // do_subscribeToKomentari("");
    }

    private void do_subscribeToKomentari(String hash) {
        MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_RESTORANI_KOMENTARI, restoran.getId()),
            new ApiSubscriberRestoranKomentari(new AuthLogin(MySession.getKorisnik()), hash,null),
            new MyAbstractRunnable<ApiSubscriberRestoranKomentari>() {
                @Override
                public void run(ApiSubscriberRestoranKomentari returnedData) {
                    if (returnedData.getKomentari() != null) {
                        komentari = returnedData.getKomentari();
                        listKomentariAdapter.notifyDataSetChanged();
                    }
                    do_subscribeToKomentari(returnedData.getKomentariHashCode());
                }

                @Override
                public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                    Log.i("Test", "Error in komentari subscribe: " + statusCode + errorMessage);
                }
            });
    }

    private void do_breakKomentariSubscription() {
        MyApiRequest.post(String.format(MyApiRequest.ENDPOINT_RESTORANI_KOMENTARI, restoran.getId()),
            new ApiSubscriberRestoranKomentari(new AuthLogin(MySession.getKorisnik()), "", null),
            new MyAbstractRunnable<ApiSubscriberRestoranKomentari>() {

                @Override
                public void run(ApiSubscriberRestoranKomentari returnedData) {
                }

                @Override
                public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                    Log.i("Test", "Error in komentari-break subscription: " + statusCode + "  -  " + errorMessage);
                }
            });
    }
}
