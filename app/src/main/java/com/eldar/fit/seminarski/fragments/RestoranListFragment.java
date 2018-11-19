package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.eldar.fit.seminarski.data.RestoranVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MyFragmentPagerAdapter;

import java.net.URL;
import java.util.List;

public class RestoranListFragment extends Fragment {

    private List<RestoranVM> storageRestorani;
    private ListView listRestorani;

    private BaseAdapter listRestoraniAdapter;

    private static RestoranListFragment fragment;
    public MyFragmentPagerAdapter.FirstPageFragmentListener listener;

    public static RestoranListFragment newInstance(MyFragmentPagerAdapter.FirstPageFragmentListener listener) {
        fragment = new RestoranListFragment();
        fragment.listener = listener;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restoran_list, container, false);

        Fragment f = this;

        listRestorani = view.findViewById(R.id.listViewRestorani);
        listRestorani.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Test", "CARD CLICKED");
                listener.onSwitchToNextFragment();
            }
        });
        popuniPodatke();

        return view;
    }

    private void popuniPodatke() {
        storageRestorani = Storage.getRestorani();

        listRestoraniAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return storageRestorani.size();
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
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    view = inflater != null ? inflater.inflate(R.layout.stavka_restoran, parent, false) : null;
                }

                TextView restoranNaziv = view.findViewById(R.id.textStavkaRestoranNaziv);
                TextView restoranOpis = view.findViewById(R.id.textStavkaRestoranOpis);
                TextView restoranLikesCount = view.findViewById(R.id.textStavkaRestoranLikes);
                ImageView restoranSlikaView = view.findViewById(R.id.imageStavkaRestoranSlika);

                restoranNaziv.setText(storageRestorani.get(position).getNaziv());
                restoranOpis.setText(storageRestorani.get(position).getOpis());
                restoranNaziv.setText(storageRestorani.get(position).getNaziv());
                restoranLikesCount.setText(storageRestorani.get(position).getLikesCount() + " sviđanja");

                /*new MyAsyncImageInflater(restoranSlika)
                    .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");*/

                Glide.with(getActivity())
                        .load(storageRestorani.get(position).getMainImageUrl())
                        .centerCrop()
                        .into(restoranSlikaView);

                return view;
            }
        };

        listRestorani.setAdapter(listRestoraniAdapter);
    }
}
