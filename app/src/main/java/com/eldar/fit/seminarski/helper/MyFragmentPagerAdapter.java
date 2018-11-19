package com.eldar.fit.seminarski.helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.eldar.fit.seminarski.fragments.KorpaFragment;
import com.eldar.fit.seminarski.fragments.PosiljkaListFragment;
import com.eldar.fit.seminarski.fragments.RestoranDetaljnoFragment;
import com.eldar.fit.seminarski.fragments.RestoranListFragment;

import java.util.Objects;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;
    private Fragment f0;
    private FragmentManager fm;
    private FirstPageListener firstPageListener = new FirstPageListener();

    public interface FirstPageFragmentListener {
        void onSwitchToNextFragment();
    }

    private final class FirstPageListener implements FirstPageFragmentListener {
        @Override
        public void onSwitchToNextFragment() {
            fm.beginTransaction()
                .remove(f0)
                .commit();

            if (f0 instanceof RestoranListFragment) {
                f0 = RestoranDetaljnoFragment.newInstance(firstPageListener);
            } else {
                f0 = RestoranListFragment.newInstance(firstPageListener);
            }
            notifyDataSetChanged();
        }
    }

    public MyFragmentPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.fm = fm;
        this.numberOfTabs = numOfTabs;
    }

    public int getTabPositionByNaziv(TabLayout tabLayout, String tabNaziv) {
        try {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                if (Objects.equals(tabLayout.getTabAt(i).getText(), tabNaziv)) {
                    return i;
                }
            }
        } catch(NullPointerException e) {
            Log.e("PagerAdapter", "NullPointer kod dohvatanja tab position-a.");
            return /*first tab*/ 0;
        }
        return /*first tab*/ 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (object instanceof RestoranListFragment && f0 instanceof RestoranDetaljnoFragment) {
            return POSITION_NONE;
        } else if (object instanceof  RestoranDetaljnoFragment && f0 instanceof  RestoranListFragment) {
            return POSITION_NONE;
        }

        return POSITION_UNCHANGED;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.i("Test", "VIEW ADAPTER GET ITEM");
                if (f0 == null) {
                    f0 = RestoranListFragment.newInstance(firstPageListener);
                }
                return f0;
            case 1:
                return KorpaFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
