package com.asifadam93.gestionnewsforum.data.network;

import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Post;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

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
import retrofit2.http.Url;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IRetrofitService {

    /**
     * Auth
     */

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

    /**
     * News
     */

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

    /**
     * Comment
     */

    @GET
    Call<List<Comment>> getComments(
            @Header("Authorization") String token,
            @Url String url
    );

    @POST("/comments")
    Call<ResponseBody> createComment(
            @Header("Authorization") String token,
            @Body Map<String, String> commentMap
    );

    @PUT("/comments/{comment_id}")
    Call<ResponseBody> updateComment(
            @Header("Authorization") String token,
            @Path("comment_id") String commentId,
            @Body Map<String, String> commentMap
    );

    @DELETE("/comments/{comment_id}")
    Call<ResponseBody> deleteComment(
            @Header("Authorization") String token,
            @Path("comment_id") String commentId
    );

    /**
     * Topic
     */

    @GET("/topics")
    Call<List<Topic>> getTopics(@Header("Authorization") String token);

    @POST("/topics")
    Call<ResponseBody> createTopic(
            @Header("Authorization") String token,
            @Body Map<String, String> topicMap
    );

    @PUT("/topics/{topic_id}")
    Call<ResponseBody> updateTopic(
            @Header("Authorization") String token,
            @Path("topic_id") String topicId,
            @Body Map<String, String> topicMap
    );

    @DELETE("/topics/{topic_id}")
    Call<ResponseBody> deleteTopic(
            @Header("Authorization") String token,
            @Path("topic_id") String topicId
    );

    /**
     * Post
     */

    @GET
    Call<List<Post>> getPosts(
            @Header("Authorization") String token,
            @Url String url
    );

    @POST("/posts")
    Call<ResponseBody> createPost(
            @Header("Authorization") String token,
            @Body Map<String, String> commentMap
    );

    @PUT("/posts/{post_id}")
    Call<ResponseBody> updatePost(
            @Header("Authorization") String token,
            @Path("post_id") String postId,
            @Body Map<String, String> postMap
    );

    @DELETE("/posts/{post_id}")
    Call<ResponseBody> deletePost(
            @Header("Authorization") String token,
            @Path("post_id") String postId
    );

}
