package com.asifadam93.gestionnewsforum.network;

import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @POST("/news")
    Call<ResponseBody> createNews(
            @Header("Authorization") String token,
            @Body Map<String, String> newsMap
    );

    @PUT("/news/{news_id}")
    Call<ResponseBody> updateNews(
            @Header("Authorization") String token,
            @Path("news_id") String newsId,
            @Body Map<String, String> newsMap
    );

    @DELETE("/news/{news_id}")
    Call<ResponseBody> deleteNews(
            @Header("Authorization") String token,
            @Path("news_id") String newsId
    );

    @GET("/topics")
    Call<List<Topic>> getTopics(@Header("Authorization") String token);
}
