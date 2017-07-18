package com.asifadam93.gestionnewsforum.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.model.Auth;
import com.asifadam93.gestionnewsforum.util.Const;
import com.asifadam93.gestionnewsforum.activities.SingInActivity;
import com.asifadam93.gestionnewsforum.model.User;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.ServiceResult;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class UserFragment extends Fragment {

    private TextView tvFirstName, tvLastName, tvEmail;
    private Button buttonUpdate, buttonSigOut;
    private RetrofitService retrofitService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Log.i("UserFragment", "onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        initViews(rootView);

        initOnClickListeners();

        //getUserData();

        return rootView;
    }

    private void initViews(View mView) {
        tvFirstName = (TextView) mView.findViewById(R.id.update_first_name);
        tvLastName = (TextView) mView.findViewById(R.id.update_last_name);
        tvEmail = (TextView) mView.findViewById(R.id.update_email);
        buttonUpdate = (Button) mView.findViewById(R.id.update_button);
        buttonSigOut = (Button) mView.findViewById(R.id.update_disconnect_button);
    }

    private void initOnClickListeners() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });
        buttonSigOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOutUser();
            }
        });
    }

    private void signOutUser(){

        Const.deleteToken(); // delete auth token

        getActivity().finish();
        startActivity(new Intent(getActivity(), SingInActivity.class));
    }

    private void updateUser() {

        final String firstName = tvFirstName.getText().toString();
        String lastName = tvLastName.getText().toString();
        String email = tvEmail.getText().toString();

        if (firstName.isEmpty()) {
            tvFirstName.setError(getString(R.string.empty_field));
            return;
        } else if (lastName.isEmpty()) {
            tvLastName.setError(getString(R.string.empty_field));
            return;
        } else if (email.isEmpty()) {
            tvEmail.setError(getString(R.string.empty_field));
            return;
        }

        Map<String,String> updateMap = new HashMap<>();
        updateMap.put("lastname",lastName);
        updateMap.put("firstname",firstName);
        updateMap.put("email",email);

        RetrofitService.getInstance().updateUser(getToken(), updateMap, new IServiceResultListener<String>() {
            @Override
            public void onResult(ServiceResult<String> result) {

                if(result != null){
                    Toast.makeText(getActivity(),firstName+" profile updated",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),result.getErrorMsg(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private String getToken() {

        String token = Const.getToken();

        if (token != null) {
            return token;
        }

        // token == null
        Toast.makeText(getActivity(), R.string.token_error, Toast.LENGTH_SHORT).show();
        getActivity().finish();
        return null;
    }

    private void getUserData() {

        String token = Const.getToken();

        if (token != null) {

            RetrofitService.getInstance().getUser(token, new IServiceResultListener<User>() {
                @Override
                public void onResult(ServiceResult<User> result) {

                    User user = result.getData();

                    if (user != null) {
                        setUserDataToView(user);
                        //Const.putPref(Const.USER_ID,user.getId(),getActivity()); // save user id
                    } else {
                        Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } else {
            // on token error, kill the activity
            Toast.makeText(getActivity(), "Token error", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

    }

    private void setUserDataToView(User user) {
        tvFirstName.setText(user.getFirstName());
        tvLastName.setText(user.getLastName());
        tvEmail.setText(user.getEmail());
    }


}
