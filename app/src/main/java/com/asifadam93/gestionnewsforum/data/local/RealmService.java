package com.asifadam93.gestionnewsforum.data.local;

import com.asifadam93.gestionnewsforum.data.IService;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Post;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Asifadam93 on 18/07/2017.
 */

public class RealmService implements IService {

    final Realm realm = Realm.getDefaultInstance();
    static RealmService realmService;

    public static RealmService getInstance() {
        if (realmService == null) {
            realmService = new RealmService();
        }
        return realmService;
    }

    @Override
    public void login(Map<String, String> loginMap, IServiceResultListener<String> result) {

    }

    @Override
    public void subscribe(Map<String, String> subscribeMap, IServiceResultListener<String> result) {

    }

    @Override
    public void getUser(String token, IServiceResultListener<User> result) {

    }

    @Override
    public void updateUser(String token, Map<String, String> updateMap, IServiceResultListener<String> result) {

    }

    @Override
    public void createNews(String token, Map<String, String> newsMap, IServiceResultListener<String> result) {

    }

    @Override
    public void getNewsList(String token, IServiceResultListener<List<News>> result)
    {

        List<News> listeNews = new ArrayList<News>();

        final Realm realm = Realm.getDefaultInstance();
        RealmResults<News> news = realm.where(News.class).findAll();


        listeNews.addAll(news);

        ServiceResult<List<News>> resultTempo =  new ServiceResult<List<News>>();

        resultTempo.setData(listeNews);

        result.onResult(resultTempo);
    }

    @Override
    public void updateNews(String token, String newsId, Map<String, String> newsMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void deleteNews(String token, String newsId, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void getComments(String token, String newsUrl, IServiceResultListener<List<Comment>> resultListener) {

    }

    @Override
    public void createComment(String token, Map<String, String> commentMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void updateComment(String token, String commentId, Map<String, String> commentMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void deleteComment(String token, String commentId, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void getTopicList(String token, IServiceResultListener<List<Topic>> result)     {

        List<Topic> listeNews = new ArrayList<Topic>();

        final Realm realm = Realm.getDefaultInstance();
        RealmResults<Topic> topics = realm.where(Topic.class).findAll();


        listeNews.addAll(topics);

        ServiceResult<List<Topic>> resultTempo =  new ServiceResult<List<Topic>>();

        resultTempo.setData(listeNews);

        result.onResult(resultTempo);
    }

    @Override
    public void createTopic(String token, Map<String, String> topicMap, IServiceResultListener<String> result) {

    }

    @Override
    public void updateTopic(String token, String topicId, Map<String, String> topicMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void deleteTopic(String token, String topicId, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void getPost(String token, String postUrl, IServiceResultListener<List<Post>> resultListener) {

    }

    @Override
    public void createPost(String token, Map<String, String> postMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void updatePost(String token, String postId, Map<String, String> postMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void deletePost(String token, String postId, IServiceResultListener<String> resultListener) {

    }
}
