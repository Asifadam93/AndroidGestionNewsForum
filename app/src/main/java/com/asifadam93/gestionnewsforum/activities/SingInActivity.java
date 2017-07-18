package com.asifadam93.gestionnewsforum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.fragments.LoginFragment;
import com.asifadam93.gestionnewsforum.util.Const;

public class SingInActivity extends Activity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        String token = Const.getToken();

        if (token != null && !token.isEmpty()) {
            // if token exist, show directly newsFragment
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // if token empty, start sign in activity
            getFragmentManager().beginTransaction().replace(R.id.frameContainer, getLoginFragment()).commit();
        }
    }

    public LoginFragment getLoginFragment() {

        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }

        return loginFragment;
    }
}
