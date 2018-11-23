package com.eldar.fit.seminarski.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.eldar.fit.seminarski.data.RestoranVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;

import java.util.List;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;

public class RestoranListFragment extends Fragment {

    private List<RestoranVM> storageRestorani;
    private ListView listRestorani;
    public static RestoranListFragment newInstance() {
        Bundle args = new Bundle();

        RestoranListFragment fragment = new RestoranListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restoran_list, container, false);

        listRestorani = view.findViewById(R.id.listViewRestorani);

        popuniPodatke();

        listRestorani.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Test", "CARD CLICKED");
                RestoranVM restoran = storageRestorani.get(position);
                do_transitionCardView(view, restoran);
            }
        });

        return view;
    }

    private void do_transitionCardView(View view, RestoranVM restoran) {
        Intent intent = new Intent(getActivity(), RestoranDetaljnoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DETAIL_VIEW_RESTORAN, restoran);
        intent.putExtras(bundle);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), view, getString(R.string.transition_restoran_card));

        startActivity(intent, options.toBundle());
    }

    private void popuniPodatke() {
        storageRestorani = Storage.getRestorani();

        listRestoraniAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return storageRestorani.size();
            }

            @Override
            public Object getItem(int position) { return storageRestorani.get(position); }

            @Override
            public long getItemId(int position) {
                return storageRestorani.get(position).getId();
            }

            @Override
            public View getView(int position, View view, ViewGroup parent) {

                if (view == null) {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.stavka_restoran, parent, false) : null;
                }

                TextView restoranNaziv = view.findViewById(R.id.textStavkaRestoranNaziv);
                TextView restoranOpis = view.findViewById(R.id.textStavkaRestoranOpis);
                TextView restoranLikesCount = view.findViewById(R.id.textStavkaRestoranLikes);
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

                        KorisnikPretragaDialogFragment dlg = KorisnikPretragaDialogFragment.newInstance(callback);
                        MyFragmentHelper.dodajDialog((AppCompatActivity)getActivity(), "promijeniPrimaocaDialog", dlg);
                    }
                });

                restoranNaziv.setText(storageRestorani.get(position).getNaziv());

                int limit = 90;
                String opis;
                if (storageRestorani.get(position).getOpis().length() > limit ) {
                    Log.i("Test", "Truncated");
                    opis = storageRestorani.get(position).getOpis().substring(0, limit - 3) + "...";
                } else {
                    opis = storageRestorani.get(position).getOpis();
                }
                restoranOpis.setText(opis);


                restoranLikesCount.setText(storageRestorani.get(position).getLikesCount() + " sviđanja");



                Glide.with(getActivity())
                        .load(storageRestorani.get(position).getMainImageUrl())
                        .centerCrop()
                        .into(restoranSlikaView);



                return view;
            }
        };

        listRestorani.setAdapter(listRestoraniAdapter);
    }

    private BaseAdapter listRestoraniAdapter;
}
