package com.eldar.fit.seminarski.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.RestoranVM;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;

public class RestoranInfoFragment extends Fragment {

    TextView restoranNaziv, restoranOpis, restoranLikesCount, titleDetaljnoRestoranNaziv;
    ImageView restoranSlika;

    private RestoranVM restoran;

    public static RestoranInfoFragment newInstance(RestoranVM restoran) {
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_VIEW_RESTORAN, restoran);

        RestoranInfoFragment fragment = new RestoranInfoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restoran_info_fragment, container, false);
        restoran = (RestoranVM) getArguments().getSerializable(DETAIL_VIEW_RESTORAN);

        restoranNaziv = view.findViewById(R.id.textDetaljnoRestoranNaziv);
        restoranNaziv.setText(restoran.getNaziv());

        titleDetaljnoRestoranNaziv = view.findViewById(R.id.titleDetaljnoRestoranNaziv);
        titleDetaljnoRestoranNaziv.setText(restoran.getNaziv());

        restoranOpis = view.findViewById(R.id.textDetaljnoRestoranOpis);



        restoranOpis.setText(restoran.getOpis());

        restoranLikesCount = view.findViewById(R.id.textDetaljnoRestoranLikes);
        restoranLikesCount.setText(restoran.getLikesCount() + " sviđanja");

        restoranSlika = view.findViewById(R.id.imageDetaljnoRestoranSlika);
        Glide.with(this)
                .load(restoran.getMainImageUrl())
                .centerCrop()
                .into(restoranSlika);

        return view;
    }
}
