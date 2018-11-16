package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MySession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KorisnikVM k = MySession.getKorisnik();

        if (k == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, GlavniActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
