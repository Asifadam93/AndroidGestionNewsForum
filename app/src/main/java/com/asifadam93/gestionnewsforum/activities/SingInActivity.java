package com.asifadam93.gestionnewsforum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.Util.Const;
import com.asifadam93.gestionnewsforum.fragments.LoginFragment;

public class SingInActivity extends Activity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // TODO: 12/07/2017 change
        //startActivity(new Intent(this,MainActivity.class));
        Const.putPref(Const.TOKEN,"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI1OTUzYWMxN2E2" +
                "ZTZmNDAwMTFkN2RiNmQiLCJyb2xlIjp7Il9pZCI6IjU5MzA4MTc2ZTIxN2MzMDAxMWNjMDFjYSIsInRpdGx" +
                "lIjoibWVtYmVyIiwibGV2ZWwiOjIsIl9fdiI6MH0sImlhdCI6MTUwMDA2MTc0M30.yqLvGNHw4LJdJ6Dy9x" +
                "pCVsn6embHvNIMLN-KScgjN84",this);

        showLoginFragment();
    }

    private void showLoginFragment() {
        getFragmentManager().beginTransaction().replace(R.id.frameContainer, getLoginFragment()).commit();
    }

    public LoginFragment getLoginFragment() {

        if(loginFragment == null){
            loginFragment = new LoginFragment();
        }

        return loginFragment;
    }
}
