package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.eldar.fit.seminarski.fragments.KorpaFragment;
import com.eldar.fit.seminarski.fragments.RestoranListFragment;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MySession;

public class GlavniActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    BottomNavigationView bottomnavigationMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (MySession.getKorisnik() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        bottomnavigationMain = findViewById(R.id.bottomnavigationMain);
        bottomnavigationMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.bottommain_restorani:
                        MyFragmentHelper.fragmentReplace(GlavniActivity.this,
                                R.id.fragmentContainer,
                                RestoranListFragment.newInstance(false),
                                RestoranListFragment.Tag,
                                false);
                        break;
                    case R.id.bottommain_omiljeni_restorani:
                        MyFragmentHelper.fragmentReplace(GlavniActivity.this,
                                R.id.fragmentContainer,
                                RestoranListFragment.newInstance(true),
                                RestoranListFragment.Tag,
                                false);
                        break;
                    case R.id.bottommain_korpa:
                        MyFragmentHelper.fragmentReplace(GlavniActivity.this,
                                R.id.fragmentContainer,
                                KorpaFragment.newInstance(false),
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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupMainNavigation();

        MyFragmentHelper.fragmentReplace(this,
                R.id.fragmentContainer,
                RestoranListFragment.newInstance(false),
                RestoranListFragment.Tag,
                false);
    }


    private void setupMainNavigation() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_restorani) {
            MyFragmentHelper.fragmentReplace(this,
                    R.id.fragmentContainer,
                    RestoranListFragment.newInstance(false),
                    RestoranListFragment.Tag,
                    false);
        } else if (id == R.id.nav_omiljeni) {
            MyFragmentHelper.fragmentReplace(this,
                    R.id.fragmentContainer,
                    RestoranListFragment.newInstance(true),
                    RestoranListFragment.Tag,
                    false);
        } else if (id == R.id.nav_korpa) {
            MyFragmentHelper.fragmentReplace(this,
                    R.id.fragmentContainer,
                    KorpaFragment.newInstance(false),
                    KorpaFragment.Tag,
                    false);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfilActivity.class));
        } else if (id == R.id.nav_logout) {

            MyApiRequest.get(MyApiRequest.ENDPOINT_USER_LOGOUT, new MyAbstractRunnable<Object>() {
                @Override
                public void run(Object o) {
                    onLogout(null, null);
                }

                @Override
                public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                    onLogout(statusCode, errorMessage);
                }
            });

        }

        navigationView.setCheckedItem(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onLogout(Integer statusCode, String errorMessage) {
        // Ako se desi greška na logout, izbaci sve podatke iz sharedprefs.
            // nećemo hendlati grešku, jer u slučaju greške na serveru će samo ostati neiskoristiv token u bazi
        Snackbar.make(findViewById(R.id.content), "Uspješan logout", Snackbar.LENGTH_SHORT).show();
        MySession.setKorisnik(null);
        MySession.setKorpa(null);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
