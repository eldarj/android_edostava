package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.fragments.PosiljkaAddPrimaocFragment;
import com.eldar.fit.seminarski.fragments.PosiljkaListFragment;
import com.eldar.fit.seminarski.fragments.RestoranDetaljnoFragment;
import com.eldar.fit.seminarski.fragments.RestoranListFragment;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;
import com.eldar.fit.seminarski.helper.MyFragmentPagerAdapter;
import com.eldar.fit.seminarski.helper.MySession;

import static com.eldar.fit.seminarski.fragments.PosiljkaAddPrimaocFragment.BUNDLE_INPUT_ADRESA_PRIMAOCA;
import static com.eldar.fit.seminarski.fragments.PosiljkaAddPrimaocFragment.BUNDLE_INPUT_IME_PRIMAOCA;

public class GlavniActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyFragmentHelper.OnKorisnikPretragaClickListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    TabLayout tabLayoutNav;

    ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayoutNav = (TabLayout) findViewById(R.id.tabLayoutNav);

        setupTabNavigation();
        setupMainNavigation();

        //MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer,  RestoranListFragment.newInstance());
    }

    private void setupTabNavigation() {
        tabLayoutNav.addTab(tabLayoutNav.newTab().setText("Restorani"));
        tabLayoutNav.addTab(tabLayoutNav.newTab().setText("Korpa"));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), tabLayoutNav.getTabCount());

        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutNav));
        tabLayoutNav.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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
        } else if (viewPager.getCurrentItem() == 0 && myFragmentPagerAdapter.getItem(0) instanceof RestoranDetaljnoFragment) {
            ((RestoranDetaljnoFragment) myFragmentPagerAdapter.getItem(0)).backPressed();
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
            tabLayoutNav.getTabAt(0).select();
            //MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer, RestoranListFragment.newInstance());
        } else if (id == R.id.nav_gallery) {
            tabLayoutNav.getTabAt(1).select();
            //MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer, PosiljkaListFragment.newInstance());
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            MySession.setKorisnik(null);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * FIND: "FIRST APPROACH: USING INTERFACE LISTENERS"
     */
    @Override
    public void EmitKorisnikPretragaClicked(KorisnikVM korisnik) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_INPUT_IME_PRIMAOCA, getString(R.string.posiljka_dodaj_primaoc_ime,
                korisnik.getIme(),
                korisnik.getPrezime()));

        args.putString(BUNDLE_INPUT_ADRESA_PRIMAOCA, getString(R.string.posiljka_dodaj_primaoc_adresa,
                korisnik.getBlokVM().getNaziv(),
                korisnik.getBlokVM().getDrzava()));

        PosiljkaAddPrimaocFragment fragment = PosiljkaAddPrimaocFragment.newInstance();
        fragment.setArguments(args);

        MyFragmentHelper.fragmentCreate(this, R.id.fragmentContainer, fragment);
        Toast.makeText(this, "Uspješno ste promijenili primaoca!", Toast.LENGTH_SHORT)
                .show();
    }
}
