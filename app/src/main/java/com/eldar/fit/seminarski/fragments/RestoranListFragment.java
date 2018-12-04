package com.eldar.fit.seminarski.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.RestoranDetaljnoActivity;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.RestoranPrikazVM;
import com.eldar.fit.seminarski.helper.MyTaggedFragment;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.RestoranInfo;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;
import static com.eldar.fit.seminarski.helper.MyApiRequest.ENDPOINT_RESTORANI;

public class RestoranListMyTaggedFragment extends Fragment implements MyTaggedFragment {

    private RestoranPrikazVM apiRestorani;
    private ListView listRestorani;
    public static RestoranListMyTaggedFragment newInstance() {
        Bundle args = new Bundle();

        RestoranListMyTaggedFragment fragment = new RestoranListMyTaggedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.restoran_list_fragment, container, false);

        listRestorani = view.findViewById(R.id.listViewRestorani);

        MyApiRequest.get(getActivity(), ENDPOINT_RESTORANI, new MyAbstractRunnable<RestoranPrikazVM>() {
            @Override
            public void run(RestoranPrikazVM restoranPrikazVM) {
                popuniPodatke(restoranPrikazVM);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                        errorMessage != null ? errorMessage : "Dogodila se greška.",
                        Snackbar.LENGTH_LONG).show();
            }
        });

        listRestorani.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Test", "CARD CLICKED");
                RestoranInfo restoran = apiRestorani.restorani.get(position);
                do_transitionCardView(view, restoran);
            }
        });

        return view;
    }

    private void do_transitionCardView(View view, RestoranInfo restoran) {
        Intent intent = new Intent(getActivity(), RestoranDetaljnoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DETAIL_VIEW_RESTORAN, restoran);
        intent.putExtras(bundle);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), view, getString(R.string.transition_restoran_card));

        try {
            startActivity(intent, options.toBundle());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Test", "error " + e.getMessage());
        }
    }

    private void popuniPodatke(RestoranPrikazVM model) {
        apiRestorani = model;

        listRestoraniAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return apiRestorani.restorani.size();
            }

            @Override
            public Object getItem(int position) { return apiRestorani.restorani.get(position); }

            @Override
            public long getItemId(int position) {
                return apiRestorani.restorani.get(position).getId();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {

                if (view == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.restoran_stavka, parent, false) : null;
                }

                TextView restoranNaziv = view.findViewById(R.id.textStavkaRestoranNaziv);
                TextView restoranOpis = view.findViewById(R.id.textStavkaRestoranOpis);
                TextView restoranLikesCount = view.findViewById(R.id.textStavkaRestoranLikes);
                TextView textStavkaRestoranLokacija = view.findViewById(R.id.textStavkaRestoranLokacija);
                ImageView restoranSlikaView = view.findViewById(R.id.imageStavkaRestoranSlika);

                MaterialButton btnDetaljno = view.findViewById(R.id.btnStavkaRestoranDetaljno);
                btnDetaljno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyFragmentHelper.RunnableCallback<KorisnikVM> callback = new MyFragmentHelper.RunnableCallback<KorisnikVM>() {
                            @Override
                            public void run(KorisnikVM korisnikVM) {

                            }
                        };

                    }
                });

                restoranNaziv.setText(apiRestorani.restorani.get(position).getNaziv());

                int limit = 120;
                String opis;
                if (apiRestorani.restorani.get(position).getOpis().length() > limit ) {
                    Log.i("Test", "Truncated");
                    opis = apiRestorani.restorani.get(position).getOpis().substring(0, limit - 3) + "...";
                } else {
                    opis = apiRestorani.restorani.get(position).getOpis();
                }
                restoranOpis.setText(opis);

                restoranLikesCount.setText(apiRestorani.restorani.get(position).getLikesCount() + " sviđanja");

                textStavkaRestoranLokacija.setText(apiRestorani.restorani.get(position).getLokacija());

                if (apiRestorani.restorani.get(position).getSlika() != null) {
                    Log.i("Test", "SLIKA " + apiRestorani.restorani.get(position).getSlika());
                    Glide.with(getActivity())
                            .load(apiRestorani.restorani.get(position).getSlika())
                            .centerCrop()
                            .into(restoranSlikaView);
                }

                return view;
            }
        };

        listRestorani.setAdapter(listRestoraniAdapter);
    }

    private BaseAdapter listRestoraniAdapter;

    @Override
    public String myFragmentTag() {
        return this.getClass().getName();
    }
}
