package com.eldar.fit.seminarski;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eldar.fit.seminarski.fragments.ProfilInfoFragment;
import com.eldar.fit.seminarski.helper.MyFragmentHelper;

public class ProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        MyFragmentHelper.fragmentReplace(this,
                R.id.fragmentProfilContainer,
                ProfilInfoFragment.newInstance(),
                ProfilInfoFragment.Tag,
                false);

    }

}
