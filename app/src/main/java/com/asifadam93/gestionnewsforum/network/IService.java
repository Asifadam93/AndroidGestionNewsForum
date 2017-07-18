package com.asifadam93.gestionnewsforum.network;

import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IService {

    /**
     * Auth
     */

    void login(Map<String, String> loginMap, IServiceResultListener<String> result);

    void subscribe(Map<String, String> subscribeMap, IServiceResultListener<String> result);

    void getUser(String token, IServiceResultListener<User> result);

    void updateUser(String token, Map<String, String> updateMap, IServiceResultListener<String> result);

    /**
     * News
     */

    void createNews(String token, Map<String, String> newsMap, IServiceResultListener<String> result);

    void getNewsList(String token, IServiceResultListener<List<News>> result);

    void updateNews(String token, String newsId, Map<String, String> newsMap, IServiceResultListener<String> resultListener);

    void deleteNews(String token, String newsId, IServiceResultListener<String> resultListener);

    /**
     * Comment
     */

    void getComments(String token, String newsUrl, IServiceResultListener<List<Comment>> resultListener);

    void createComment(String token, Map<String, String> commentMap, IServiceResultListener<String> resultListener);

    void updateComment(String token, String commentId, Map<String, String> commentMap, IServiceResultListener<String> resultListener);

    void deleteComment(String token, String commentId, IServiceResultListener<String> resultListener);

    /**
     * Topic
     */

    void getTopicList(String token, IServiceResultListener<List<Topic>> result);

    void createTopic(String token, Map<String, String> topicMap, IServiceResultListener<String> result);

    void updateTopic(String token, String topicId, Map<String, String> topicMap, IServiceResultListener<String> resultListener);

    void deleteTopic(String token, String topicId, IServiceResultListener<String> resultListener);

    /**
     * Comment
     */

    void getPost(String token, String postUrl, IServiceResultListener<List<Comment>> resultListener);

    void createPost(String token, Map<String, String> postMap, IServiceResultListener<String> resultListener);

    void updatePost(String token, String postId, Map<String, String> postMap, IServiceResultListener<String> resultListener);

    void deletePost(String token, String postId, IServiceResultListener<String> resultListener);
}