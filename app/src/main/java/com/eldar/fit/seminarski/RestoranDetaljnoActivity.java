package com.eldar.fit.seminarski;

import android.support.design.card.MaterialCardView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eldar.fit.seminarski.data.RestoranVM;

public class RestoranDetaljnoActivity extends AppCompatActivity {

    public static String DETAIL_VIEW_RESTORAN = "detailViewRestoran";

    TextView restoranNaziv, restoranOpis, restoranLikesCount, customToolbarTitle;
    ImageView restoranSlika;
    Toolbar transparentToolbar;

    private Animation anim;
    private MaterialCardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_detaljno);

        Bundle args = getIntent().getExtras();
        RestoranVM restoran = (RestoranVM) args.getSerializable(DETAIL_VIEW_RESTORAN);

        restoranNaziv = findViewById(R.id.textDetaljnoRestoranNaziv);
        restoranNaziv.setText(restoran.getNaziv());

        restoranOpis = findViewById(R.id.textDetaljnoRestoranOpis);
        restoranOpis.setText(restoran.getOpis());

        restoranLikesCount = findViewById(R.id.textDetaljnoRestoranLikes);
        restoranLikesCount.setText(restoran.getLikesCount() + " sviđanja");

        restoranSlika = findViewById(R.id.imageDetaljnoRestoranSlika);
        Glide.with(this)
                .load(restoran.getMainImageUrl())
                .centerCrop()
                .into(restoranSlika);

    }

    @Override
    public void onAttachedToWindow() {
        //animateCard();
    }

    private void animateCard() {
        card = findViewById(R.id.cardDetaljnoContainer);
        anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) card.getLayoutParams();
                int m = params.getMarginStart();
                Log.i("test", "happening + " + m);
                int interpolatedMargin = (int) (16 * interpolatedTime);
                params.setMargins(interpolatedMargin, interpolatedMargin, interpolatedMargin, interpolatedMargin);
                super.applyTransformation(interpolatedTime, t);
            }
        };
        anim.setDuration(250);
        card.setAnimation(anim);
        card.invalidate();
    }
}
