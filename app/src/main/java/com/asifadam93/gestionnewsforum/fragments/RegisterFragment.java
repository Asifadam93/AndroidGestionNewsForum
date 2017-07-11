package com.asifadam93.gestionnewsforum.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.network.IServiceResultListener;
import com.asifadam93.gestionnewsforum.network.RetrofitService;
import com.asifadam93.gestionnewsforum.network.ServiceResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AAD on 15/06/2017.
 */

public class RegisterFragment extends Fragment {

    private View view;
    private EditText editTextLastName, editTextFirstName, editTextEmail, editTextPassword, editTextConfirmMdp;
    private Button buttonInscription;
    private TextView textViewConnexion;
    private LoginFragment loginFragment;
    private RetrofitService retrofitUserService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.register_layout, container, false);
        initView();

        buttonInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userVerification();
            }
        });

        // goto login screen
        textViewConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginFragment();
            }
        });

        return view;
    }

    private void initView() {
        editTextLastName = (EditText) view.findViewById(R.id.inscription_nom);
        editTextFirstName = (EditText) view.findViewById(R.id.inscription_prenom);
        editTextEmail = (EditText) view.findViewById(R.id.inscription_email);
        editTextPassword = (EditText) view.findViewById(R.id.inscription_mdp);
        editTextConfirmMdp = (EditText) view.findViewById(R.id.inscription_confirmation_mdp);
        buttonInscription = (Button) view.findViewById(R.id.inscription_button);
        textViewConnexion = (TextView) view.findViewById(R.id.inscription_connexion);
    }

    private void userVerification() {

        // get user info from editTexts
        String nom = editTextLastName.getText().toString();
        String prenom = editTextFirstName.getText().toString();
        String email = editTextEmail.getText().toString();
        String mdp = editTextPassword.getText().toString();
        String confirmMdp = editTextConfirmMdp.getText().toString();

        // empty field verification
        if (nom.isEmpty()) {
            editTextLastName.setError(getString(R.string.empty_field));
            return;
        }

        if (prenom.isEmpty()) {
            editTextFirstName.setError(getString(R.string.empty_field));
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.empty_field));
            return;
        }

        if (mdp.isEmpty()) {
            editTextPassword.setError(getString(R.string.empty_field));
            return;
        }

        if (confirmMdp.isEmpty()) {
            editTextConfirmMdp.setError(getString(R.string.empty_field));
            return;
        }

        // password comparison
        if (!mdp.equals(confirmMdp)) {
            editTextPassword.setError(getString(R.string.dif_password));
            return;
        }

        HashMap<String, String> hashMapUser = new HashMap<>();
        hashMapUser.put("firstname", prenom);
        hashMapUser.put("lastname", nom);
        hashMapUser.put("email", email);
        hashMapUser.put("password", mdp);

        registerUser(hashMapUser);
    }

    private void registerUser(final Map<String, String> registerMap) {

        getRetrofitUserService().subscribe(registerMap, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {

                if (result != null) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.inscription), Toast.LENGTH_SHORT).show();
                    showLoginFragment();
                } else {
                    Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showLoginFragment() {
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, getLoginFragment(), "LoginFragment")
                .commit();
    }

    public LoginFragment getLoginFragment() {
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        return loginFragment;
    }

    private RetrofitService getRetrofitUserService() {
        if (retrofitUserService == null) {
            retrofitUserService = new RetrofitService(getActivity());
        }
        return retrofitUserService;
    }
}
