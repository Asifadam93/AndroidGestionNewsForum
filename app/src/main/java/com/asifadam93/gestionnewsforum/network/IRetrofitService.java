package com.asifadam93.gestionnewsforum.network;

import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IRetrofitService {

    @Headers("Content-Type: application/json")
    @POST("/auth/login")
    Call<String> login(@Body Map<String, String> loginMap);

    @POST("/auth/subscribe")
    Call<String> subscribe(@Body Map<String, String> subscribeMap);

    @GET("/users/me")
    Call<User> getUser(@Header("Authorization") String token);

    @PUT("/users/me")
    Call<String> updateUser(
            @Header("Authorization") String token,
            @Body Map<String, String> updateMap
    );

    @GET("/news")
    Call<List<News>> getNews(@Header("Authorization") String token);

    @GET("/topics")
    Call<List<Topic>> getTopics(@Header("Authorization") String token);
}
