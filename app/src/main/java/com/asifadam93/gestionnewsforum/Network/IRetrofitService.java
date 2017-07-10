package com.asifadam93.gestionnewsforum.Network;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IRetrofitService {

    @POST("/auth/login")
    Call<String> login(Map<String,String> loginMap);

    @POST("/auth/subscribe")
    Call<String> subscribe(Map<String,String> subscribeMap);

}
