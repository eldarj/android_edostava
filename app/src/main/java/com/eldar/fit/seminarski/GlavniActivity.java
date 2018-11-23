package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.fragments.KorisnikPretragaDialogFragment;
import com.eldar.fit.seminarski.fragments.KorpaFragment;
import com.eldar.fit.seminarski.fragments.ProfilActivity;
import com.eldar.fit.seminarski.fragments.RestoranListFragment;
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

        KorisnikVM k = MySession.getKorisnik();

        if (k == null) {
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
                int kliknutiTabId = menuItem.getItemId();

                switch(kliknutiTabId) {
                    case R.id.bottommain_restorani:
                        MyFragmentHelper.fragmentCreate(GlavniActivity.this,
                                R.id.fragmentContainer,
                                RestoranListFragment.newInstance());
                        break;
                    case R.id.bottommain_korpa:
                        MyFragmentHelper.fragmentCreate(GlavniActivity.this,
                                R.id.fragmentContainer,
                                KorpaFragment.newInstance());
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


        MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer,  RestoranListFragment.newInstance());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_secondary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_restorani) {
            MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer,  RestoranListFragment.newInstance());
        } else if (id == R.id.nav_korpa) {
            MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer, KorpaFragment.newInstance());
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfilActivity.class));
//            DialogFragment dlg = new KorisnikPretragaDialogFragment();
//            MyFragmentHelper.dodajDialog(this, "testTag", dlg);
        } else if (id == R.id.nav_logout) {
            MySession.setKorisnik(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        navigationView.setCheckedItem(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
