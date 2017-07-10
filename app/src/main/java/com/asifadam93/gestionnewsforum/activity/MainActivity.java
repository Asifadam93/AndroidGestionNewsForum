package com.asifadam93.gestionnewsforum.activity;

import android.app.Activity;
import android.os.Bundle;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.fragments.LoginFragment;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLoginFragment();
    }

    private void showLoginFragment() {
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, new LoginFragment()).commit();
    }
}
