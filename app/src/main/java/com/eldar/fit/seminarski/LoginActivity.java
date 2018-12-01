package com.eldar.fit.seminarski;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.eldar.fit.seminarski.data.AuthLogin;
import com.eldar.fit.seminarski.data.KorisnikVM;
import com.eldar.fit.seminarski.helper.MyAbstractRunnable;
import com.eldar.fit.seminarski.helper.MyApiRequest;
import com.eldar.fit.seminarski.helper.MySession;
import com.eldar.fit.seminarski.helper.MyUtils;

import static com.eldar.fit.seminarski.helper.MyApiRequest.ENDPOINT_USER_LOGIN_CHECK_AUTH;

public class LoginActivity extends AppCompatActivity {

    private static String BUNDLE_KEY_USERNAME = "MyBundleKeyUsername";
    private static String BUNDLE_KEY_PASSWORD = "MyBundleKeyPassword";

    private TextInputEditText inputUsername, inputPassword;
    private Button btnLogin, btnOpenRegistration;

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

        btnOpenRegistration = findViewById(R.id.btnOpenRegistration);
        btnOpenRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_btnOpenRegistrationClick();
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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_KEY_USERNAME, inputUsername.getText().toString());
        outState.putString(BUNDLE_KEY_PASSWORD, inputPassword.getText().toString());
        Log.i("TEST", "onSaveInstanceState from Login");
        super.onSaveInstanceState(outState);
    }

    private void do_btnOpenRegistrationClick() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void do_btnLoginClick() {
        MyUtils.dismissKeyboard(this);
        findViewById(R.id.progressBar_login).setVisibility(View.VISIBLE);

        if (inputPassword == null || inputPassword.length() <= 4) {
            inputPassword.setError(getString(R.string.login_error_password));
        } else {
            inputPassword.setError(null); // Clear the error
        }
        if (inputUsername == null || inputUsername.length() <= 4) {
            inputUsername.setError(getString(R.string.login_error_username));
        } else {
            inputUsername.setError(null); // Clear the error
        }

        if (inputUsername.getError() != null || inputPassword.getError() != null) {
            Snackbar.make(findViewById(android.R.id.content), "Molimo provjerite podatke!", Snackbar.LENGTH_LONG).show();
            findViewById(R.id.progressBar_login).setVisibility(View.INVISIBLE);
            return;
        }

        AuthLogin credentialsObj = new AuthLogin(inputUsername.getText().toString(), inputPassword.getText().toString());
        MyApiRequest.post(this, ENDPOINT_USER_LOGIN_CHECK_AUTH, credentialsObj, new MyAbstractRunnable<KorisnikVM>() {
            @Override
            public void run(KorisnikVM korisnikVM) {
                loginUser(korisnikVM, null, null);
            }

            @Override
            public void error(@Nullable Integer statusCode, @Nullable String errorMessage) {
                loginUser(null, statusCode, errorMessage);
            }

        });
        //KorisnikVM korisnik = Storage.LoginCheck(inputUsername.getText().toString(), inputPassword.getText().toString());
        //loginUser(korisnik);
    }

    private void loginUser(@Nullable KorisnikVM korisnik, @Nullable Integer statusCode, @Nullable String errorMessage) {
        if (findViewById(R.id.progressBar_login) != null) {
            findViewById(R.id.progressBar_login).setVisibility(View.INVISIBLE);
        }
        if (korisnik == null) {
            Snackbar.make(findViewById(android.R.id.content), errorMessage != null ? errorMessage : "Pogrešan username ili password." , Snackbar.LENGTH_LONG).show();
        } else {
            MySession.setKorisnik(korisnik);
            startActivity(new Intent(this, GlavniActivity.class));
            finish();
        }
    }
}
