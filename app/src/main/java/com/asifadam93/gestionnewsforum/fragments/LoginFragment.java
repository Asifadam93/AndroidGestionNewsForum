package com.asifadam93.gestionnewsforum.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.Const;
import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.network.IServiceResultListener;
import com.asifadam93.gestionnewsforum.network.RetrofitService;
import com.asifadam93.gestionnewsforum.network.ServiceResult;

import java.util.HashMap;

/**
 * Created by AAD on 14/06/2017.
 */

public class LoginFragment extends Fragment {

    private static FragmentManager fragmentManager; // TODO: 20/06/2017 use parcelable instead
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private View view;
    private RetrofitService retrofitService;
    private RegisterFragment registerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login_layout, container, false);

        initViews();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
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
        });

        return view;
    }

    private void initViews() {
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
            editTextEmail.setError(getString(R.string.empty_field));
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.empty_field));
            return;
        }

        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("email", email);
        userMap.put("password", password);

        getRetrofitService().login(userMap, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {

                String token = result.getData();

                if (token != null) {
                    saveToken(token);
                    Log.i("LoginFragment", "Token saved");
                } else {
                    Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    Log.i("LoginFragment", result.getErrorMsg());
                }
            }
        });
    }

    private void saveToken(String token){
        SharedPreferences pref = getActivity().getSharedPreferences(Const.sharedPrefName, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Const.token, "Bearer "+token); // Storing string
        editor.apply(); // commit changes
    }

    private RetrofitService getRetrofitService() {
        if (retrofitService == null) {
            retrofitService = new RetrofitService(getActivity());
        }
        return retrofitService;
    }

    private RegisterFragment getRegisterFragment() {
        if (registerFragment == null) {
            registerFragment = new RegisterFragment();
        }
        return registerFragment;
    }
}