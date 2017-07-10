package com.asifadam93.gestionnewsforum.network;

import android.content.Context;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public class RetrofitService implements IService {

    private IRetrofitService retrofitService;
    private Context context;

    public RetrofitService(Context context) {
        this.context = context;
    }

    @Override
    public void login(Map<String, String> loginMap, final IServiceResultListener<String> resultListener) {

        getRetrofitService().login(loginMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {

                    switch (response.code()) {
                        case 404:
                            result.setErrorMsg(context.getString(R.string.login_incorrect_details));
                            break;

                        case 400:
                            result.setErrorMsg(context.getString(R.string.login_missing_fields));
                            break;

                        default:
                            result.setErrorMsg(context.getString(R.string.login_connexion_error));
                    }
                }

                if (resultListener != null) {
                    resultListener.onResult(result);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void subscribe(Map<String, String> subscribeMap, final IServiceResultListener<String> resultListener) {

        getRetrofitService().subscribe(subscribeMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData(context.getString(R.string.inscription));
                } else {
                    result.setErrorMsg(context.getString(R.string.error_subscribe));
                }

                if (resultListener != null) {
                    resultListener.onResult(result);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void getUser(String token, final IServiceResultListener<User> resultListener) {

        getRetrofitService().getUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                ServiceResult<User> result = new ServiceResult<User>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    result.setErrorMsg(context.getString(R.string.get_user_error));
                }

                if (resultListener != null) {
                    resultListener.onResult(result);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<User>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void updateUser(String token, Map<String, String> updateMap, final IServiceResultListener<String> resultListener) {

        getRetrofitService().updateUser(token, updateMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData(context.getString(R.string.user_updated));
                } else {
                    result.setErrorMsg(context.getString(R.string.error_update_user));
                }

                if (resultListener != null) {
                    resultListener.onResult(result);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    public IRetrofitService getRetrofitService() {

        if (retrofitService == null) {
            retrofitService = RetrofitSession.getInstance().create(IRetrofitService.class);
        }

        return retrofitService;
    }
}
