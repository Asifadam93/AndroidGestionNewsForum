package com.asifadam93.gestionnewsforum.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;

import java.util.HashMap;

/**
 * Created by AAD on 14/06/2017.
 */

public class LoginFragment extends Fragment {

    private static FragmentManager fragmentManager; // TODO: 20/06/2017 use parcelable instead
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private View view;/*
    private RetrofitUserService retrofitUserService;
    private RegisterFragment registerFragment;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login_layout, container, false);

        //initViews();

        /*buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userVerification();
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().replace(R.id.frameContainer, getRegisterFragment(), "RegisterFragment").commit();
            }
        });*/

        return view;
    }

   /* private void initViews() {
        fragmentManager = getActivity().getFragmentManager();
        editTextEmail = (EditText) view.findViewById(R.id.login_email);
        editTextPassword = (EditText) view.findViewById(R.id.login_mdp);
        buttonLogin = (Button) view.findViewById(R.id.login_button);
        textViewRegister = (TextView) view.findViewById(R.id.login_inscription);
    }

    private void userVerification() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.champ_vide));
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.champ_vide));
            return;
        }

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("login", email);
        userMap.put("password", password);

        getRetrofitUserService().login(userMap, new IServiceResultListener<TokenResponse>() {
            @Override
            public void onResult(ServiceResult<TokenResponse> result) {

                Log.i("RetrofitUserService", "onResult");

                TokenResponse tokenResponse = result.getData();

                if (tokenResponse != null) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.conn_ok), Toast.LENGTH_SHORT).show();

                    // start userActivity with tokenData
                    Intent intent = new Intent(getActivity(), UserActivity.class);
                    intent.putExtra("UserInfo", tokenResponse);
                    startActivity(intent);

                } else {
                    // error
                    Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private RetrofitUserService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitUserService();
        }
        return retrofitUserService;
    }

    private RegisterFragment getRegisterFragment() {
        if (registerFragment == null) {
            registerFragment = new RegisterFragment();
        }
        return registerFragment;
    }*/
}