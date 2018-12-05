package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.ApiSubscriberRestoranKomentari;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
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
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restoran_komentari_pager_fragment, container, false);

        listViewRecenzije = view.findViewById(R.id.listViewRestoraniRecenzije);
        popuniKomentare();

        return view;
    }

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
        MyApiRequest.post(getActivity(),
                String.format(MyApiRequest.ENDPOINT_RESTORANI_KOMENTARI, restoran.getId()),
                new ApiSubscriberRestoranKomentari(new AuthLogin(MySession.getKorisnik()), hash,null),
                new MyAbstractRunnable<ApiSubscriberRestoranKomentari>() {
                    @Override
                    public void run(ApiSubscriberRestoranKomentari returnedData) {
                        if (returnedData.getRecenzije() != null) {
                            komentari = returnedData.getRecenzije();
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
        MyApiRequest.post(getActivity(),
                String.format(MyApiRequest.ENDPOINT_RESTORANI_KOMENTARI, restoran.getId()),
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

    private void popuniKomentare() {
        komentari = restoran.getRecenzije();
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
}
