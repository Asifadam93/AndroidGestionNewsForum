package com.asifadam93.gestionnewsforum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.fragments.LoginFragment;

public class SingInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // TODO: 12/07/2017 change
        startActivity(new Intent(this,MainActivity.class));

        //showLoginFragment();
    }

    private void showLoginFragment() {
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, new LoginFragment()).commit();
    }
}
