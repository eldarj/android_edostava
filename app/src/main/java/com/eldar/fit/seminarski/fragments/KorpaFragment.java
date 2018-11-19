package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eldar.fit.seminarski.R;

import java.io.Serializable;

public class KorpaFragment extends Fragment {

    public static KorpaFragment newInstance() {
        Bundle args = new Bundle();
        KorpaFragment fragment = new KorpaFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_korpa, container, false);

        return view;
    }
}
