package com.eldar.fit.seminarski;

import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;

import com.eldar.fit.seminarski.data.RestoranVM;
import com.eldar.fit.seminarski.fragments.KorpaFragment;
import com.eldar.fit.seminarski.fragments.RestoranJelovnikFragment;
import com.eldar.fit.seminarski.fragments.RestoranInfoFragment;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;

public class RestoranDetaljnoActivity extends AppCompatActivity {

    public static String DETAIL_VIEW_RESTORAN = "detailViewRestoran";
    BottomNavigationView bottomnavigationRestoranDetaljno;

    private Animation anim;
    private MaterialCardView card;
    private RestoranVM restoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_detaljno);

        Bundle args = getIntent().getExtras();
        restoran = (RestoranVM) args.getSerializable(DETAIL_VIEW_RESTORAN);

        MyFragmentHelper.fragmentCreate(this,
                R.id.restoranDetaljnoFragmentContainer,
                RestoranInfoFragment.newInstance(restoran));

        bottomnavigationRestoranDetaljno = findViewById(R.id.bottomnavigationRestoranDetaljno);
        bottomnavigationRestoranDetaljno.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int trenutniTabId = bottomnavigationRestoranDetaljno.getSelectedItemId();
                int kliknutiTabId = menuItem.getItemId();

                switch(kliknutiTabId) {
                    case R.id.nav_bottom_restoran_jelovnik:
                        MyFragmentHelper.fragmentCreate(RestoranDetaljnoActivity.this,
                                R.id.restoranDetaljnoFragmentContainer,
                                RestoranJelovnikFragment.newInstance());
                        break;
                    case R.id.nav_bottom_restoran_vise:
                        MyFragmentHelper.fragmentCreate(RestoranDetaljnoActivity.this,
                                R.id.restoranDetaljnoFragmentContainer,
                                RestoranInfoFragment.newInstance(restoran));
                        break;
                    case R.id.nav_bottom_restoran_korpa:
                        MyFragmentHelper.fragmentCreate(RestoranDetaljnoActivity.this,
                                R.id.restoranDetaljnoFragmentContainer,
                                KorpaFragment.newInstance());
                        break;
                    default:
                        return false;
                }

                menuItem.setChecked(true);
                return true;
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        //animateCard();
    }

    /*private void animateCard() {
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
    }*/
}
