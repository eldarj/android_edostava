package com.eldar.fit.seminarski.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.R;
import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.TipKuhinje;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.RestoranInfo;
import com.eldar.fit.seminarski.helper.RestoranRecenzija;
import com.eldar.fit.seminarski.helper.RestoranViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.eldar.fit.seminarski.RestoranDetaljnoActivity.DETAIL_VIEW_RESTORAN;

public class RestoranInfoFragment extends Fragment {
    public static String Tag = "restoranInfoFragment";

    private RestoranInfo restoran;

    private ImageButton btnRestoranClose;
    private ImageView restoranSlika;

    private TextView restoranNaziv;
    private TextView restoranStatsCount;
    private TextView titleDetaljnoRestoranNaziv;
    private FloatingActionButton fabDetaljnoRestoranLike;
    private ProgressBar progressBar_restoranDetaljnoLike;
    private ViewPager viewPager;
    private RestoranViewPagerAdapter pagerAdapter;

    public static RestoranInfoFragment newInstance(RestoranInfo restoran) {
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
        restoran = (RestoranInfo) getArguments().getSerializable(DETAIL_VIEW_RESTORAN);

        btnRestoranClose = view.findViewById(R.id.btnRestoranClose);
        btnRestoranClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // Restoran top-section
        restoranSlika = view.findViewById(R.id.imageDetaljnoRestoranSlika);
        Glide.with(this)
                .load(restoran.getSlika())
                .centerCrop()
                .into(restoranSlika);

        restoranNaziv = view.findViewById(R.id.textDetaljnoRestoranNaziv);
        restoranNaziv.setText(restoran.getNaziv());

        titleDetaljnoRestoranNaziv = view.findViewById(R.id.titleDetaljnoRestoranNaziv);
        titleDetaljnoRestoranNaziv.setText(restoran.getNaziv());

        restoranStatsCount = view.findViewById(R.id.textDetaljnoRestoranStats);
        restoranStatsCount.setText(restoran.getLikesCount() + " sviđanja i " + restoran.getRecenzije().size() + " komentara");

        progressBar_restoranDetaljnoLike = view.findViewById(R.id.progressBar_restoranDetaljnoLike);

        fabDetaljnoRestoranLike = view.findViewById(R.id.fabDetaljnoRestoranLike);
            fabDetaljnoRestoranLike.setBackgroundTintList(ColorStateList.valueOf(getResources()
                    .getColor(restoran.userHasLiked() ? R.color.colorPrimary : R.color.colorBackgroundDark)));
        fabDetaljnoRestoranLike.setImageDrawable(getResources()
                .getDrawable(restoran.userHasLiked() ? R.drawable.ic_heart_white : R.drawable.ic_heart));

        fabDetaljnoRestoranLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnLikeClick();
            }
        });

        viewPager = view.findViewById(R.id.restoranDetaljnoViewPager);
        prepareViewPager();

        return view;
    }

    private void prepareViewPager() {
        pagerAdapter = new RestoranViewPagerAdapter(getFragmentManager());
        ((RestoranViewPagerAdapter) pagerAdapter).addFrag(RestoranInfoPagerFragment.newInstance(restoran), RestoranInfoPagerFragment.PAGE_TITLE);
        ((RestoranViewPagerAdapter) pagerAdapter).addFrag(RestoranKomentariPagerFragment.newInstance(restoran), RestoranKomentariPagerFragment.PAGE_TITLE);
        viewPager.setAdapter(pagerAdapter);
    }

    private void do_btnLikeClick() {
        progressBar_restoranDetaljnoLike.setVisibility(View.VISIBLE);
        fabDetaljnoRestoranLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_transparent));
        if (restoran.userHasLiked()) {
            MyApiRequest.post(getActivity(),
                    String.format(MyApiRequest.ENDPOINT_RESTORANI_UNLIKE, restoran.getId()),
                    new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                    new MyAbstractRunnable<String>() {
                        @Override
                        public void run(String response) {
                            onRestoranLikeDislike(response, null, null);
                        }

                        @Override
                        public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                            onRestoranLikeDislike(null, statusCode, errorMessage);
                        }
                    });
        } else {
            MyApiRequest.post(getActivity(),
                    String.format(MyApiRequest.ENDPOINT_RESTORANI_LIKE, restoran.getId()),
                    new AuthLogin(MySession.getKorisnik().getUsername(), MySession.getKorisnik().getPassword()),
                    new MyAbstractRunnable<String>() {
                        @Override
                        public void run(String response) {
                            onRestoranLikeDislike(response, null, null);
                        }

                        @Override
                        public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                            onRestoranLikeDislike(null, statusCode, errorMessage);
                        }
                    });
        }
    }

    private void onRestoranLikeDislike(@Nullable String response,
           @Nullable Integer statusCode,
           @Nullable String errorMessage
    ) {
        progressBar_restoranDetaljnoLike.setVisibility(View.INVISIBLE);
        fabDetaljnoRestoranLike.show();
        if (response != null) {
            fabDetaljnoRestoranLike.setBackgroundTintList(ColorStateList.valueOf(getResources()
                    .getColor(restoran.toggleLike() ? R.color.colorPrimary : R.color.colorBackgroundDark)));
            fabDetaljnoRestoranLike.setImageDrawable(getResources()
                    .getDrawable(restoran.userHasLiked() ? R.drawable.ic_heart_white : R.drawable.ic_heart));

            Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                    response,
                    Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getActivity().findViewById(R.id.fragmentContainer),
                    errorMessage != null ? errorMessage : "Dogodila se greška.",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
