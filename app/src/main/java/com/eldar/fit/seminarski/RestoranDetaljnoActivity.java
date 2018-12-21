package com.eldar.fit.seminarski;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.eldar.fit.seminarski.fragments.KorpaFragment;
import com.eldar.fit.seminarski.fragments.RestoranJelovnikFragment;
import com.eldar.fit.seminarski.fragments.RestoranInfoFragment;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.data.RestoranInfo;

public class RestoranDetaljnoActivity extends AppCompatActivity {

    public static String DETAIL_VIEW_RESTORAN = "detailViewRestoran";

    public static String DETAIL_VIEW_GOTO_INFO = "gotoDetaljnoRestoranInfo";
    public static String DETAIL_VIEW_GOTO_JELOVNIK = "gotoDetaljnoRestoranJelovnik";
    public static String DETAIL_VIEW_RESTORAN_FRAGMENT_FLAG = "detailviewRestoranFragment";

    BottomNavigationView bottomnavigationRestoranDetaljno;

    private RestoranInfo restoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restoran_detaljno);

        Bundle args = getIntent().getExtras();
        restoran = (RestoranInfo) args.getSerializable(DETAIL_VIEW_RESTORAN);

        if (args.getString(DETAIL_VIEW_RESTORAN_FRAGMENT_FLAG).equals(DETAIL_VIEW_GOTO_JELOVNIK)) {
            MyFragmentHelper.fragmentReplace(this,
                    R.id.fragmentContainer,
                    RestoranJelovnikFragment.newInstance(restoran),
                    RestoranInfoFragment.Tag,
                    false);
        } else {
            MyFragmentHelper.fragmentReplace(this,
                    R.id.fragmentContainer,
                    RestoranInfoFragment.newInstance(restoran),
                    RestoranInfoFragment.Tag,
                    false);
        }

        bottomnavigationRestoranDetaljno = findViewById(R.id.bottomnavigationRestoranDetaljno);
        bottomnavigationRestoranDetaljno.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_bottom_restoran_jelovnik:
                        MyFragmentHelper.fragmentReplace(RestoranDetaljnoActivity.this,
                                R.id.fragmentContainer,
                                RestoranJelovnikFragment.newInstance(restoran),
                                RestoranJelovnikFragment.Tag,
                                false);
                        break;
                    case R.id.nav_bottom_restoran_vise:
                        MyFragmentHelper.fragmentReplace(RestoranDetaljnoActivity.this,
                                R.id.fragmentContainer,
                                RestoranInfoFragment.newInstance(restoran),
                                RestoranInfoFragment.Tag,
                                false);
                        break;
                    case R.id.nav_bottom_restoran_korpa:
                        MyFragmentHelper.fragmentReplace(RestoranDetaljnoActivity.this,
                                R.id.fragmentContainer,
                                KorpaFragment.newInstance(true),
                                KorpaFragment.Tag,
                                false);
                        break;
                    default:
                        return false;
                }

                menuItem.setChecked(true);
                return true;
            }
        });
    }
}
