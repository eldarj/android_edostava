package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.data.Storage;
import com.eldar.fit.seminarski.helper.MySession;

public class LoginActivity extends AppCompatActivity {

    private static String BUNDLE_KEY_USERNAME = "MyBundleKeyUsername";
    private static String BUNDLE_KEY_PASSWORD = "MyBundleKeyPassword";

    private EditText inputUsername, inputPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = findViewById(R.id.inputLoginUsername);

        inputPassword = findViewById(R.id.inputLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                do_btnLoginClick();
            }
        });


        if (savedInstanceState != null
            && savedInstanceState.containsKey(BUNDLE_KEY_USERNAME)) {
            inputUsername.setText(BUNDLE_KEY_USERNAME);
        }
        if (savedInstanceState != null
            && savedInstanceState.containsKey(BUNDLE_KEY_USERNAME)) {
            inputPassword.setText(BUNDLE_KEY_PASSWORD);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TEST", "onResume from Login");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TEST", "onDestroy from Login");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_KEY_USERNAME, inputUsername.getText().toString());
        outState.putString(BUNDLE_KEY_PASSWORD, inputPassword.getText().toString());
        Log.i("TEST", "onSaveInstanceState from Login");
        super.onSaveInstanceState(outState);
    }

    private void do_btnLoginClick() {

        KorisnikVM korisnik = Storage.LoginCheck(inputUsername.getText().toString(), inputPassword.getText().toString());

        if (korisnik == null) {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Pogrešan username ili password.", Snackbar.LENGTH_LONG).show();
        } else {
            MySession.setKorisnik(korisnik);
            startActivity(new Intent(this, GlavniActivity.class));
            finish();
        }

    }
}
