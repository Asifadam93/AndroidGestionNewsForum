package com.asifadam93.gestionnewsforum.fragments;

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
import com.asifadam93.gestionnewsforum.Util.Const;
import com.asifadam93.gestionnewsforum.model.User;
import com.asifadam93.gestionnewsforum.network.IServiceResultListener;
import com.asifadam93.gestionnewsforum.network.RetrofitService;
import com.asifadam93.gestionnewsforum.network.ServiceResult;


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

        Log.i("UserFragment","onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        initViews(rootView);

        initOnClickListeners();

        getUserData();

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

            }
        });

        buttonSigOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void getUserData() {

        String token = Const.getPref(Const.TOKEN, getActivity());

        if (token != null) {

            getRetrofitService().getUser(token, new IServiceResultListener<User>() {
                @Override
                public void onResult(ServiceResult<User> result) {

                    User user = result.getData();

                    if (user != null) {
                        setUserDataToView(user);
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

    public RetrofitService getRetrofitService() {

        if (retrofitService == null) {
            retrofitService = new RetrofitService(getActivity());
        }

        return retrofitService;
    }

}
