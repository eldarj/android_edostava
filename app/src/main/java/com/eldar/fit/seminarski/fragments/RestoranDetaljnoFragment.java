package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.helper.MyFragmentPagerAdapter;

public class RestoranDetaljnoFragment extends Fragment {

    private TextView RestoranNaziv;
    private MyFragmentPagerAdapter.FirstPageFragmentListener listener;

    public static RestoranDetaljnoFragment newInstance(MyFragmentPagerAdapter.FirstPageFragmentListener listener) {
        RestoranDetaljnoFragment fragment = new RestoranDetaljnoFragment();
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restoran_detaljno, container, false);

        RestoranNaziv = view.findViewById(R.id.tvRestoranNaziv);

        return view;
    }

    public void backPressed() {
        Log.i("Test", "BACK PRESSED :: RESTORAN DETALJNO");
        listener.onSwitchToNextFragment();
    }
}
