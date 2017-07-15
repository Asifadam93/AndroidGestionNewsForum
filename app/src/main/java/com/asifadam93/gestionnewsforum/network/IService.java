package com.asifadam93.gestionnewsforum.network;

import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IService {

    void login(Map<String, String> loginMap, IServiceResultListener<String> result);

    void subscribe(Map<String, String> subscribeMap, IServiceResultListener<String> result);

    void getUser(String token, IServiceResultListener<User> result);

    void updateUser(String token, Map<String, String> updateMap, IServiceResultListener<String> result);

    void createNews(String token, Map<String,String> newsMap, IServiceResultListener<String> result);

    void getNewsList(String token, IServiceResultListener<List<News>> result);

    void updateNews(String token, String newsId, Map<String,String> newsMap, IServiceResultListener<String> resultListener);

    void deleteNews(String token, String newsId, IServiceResultListener<String> resultListener);

    void getTopicList(String token, IServiceResultListener<List<Topic>> result);

}
