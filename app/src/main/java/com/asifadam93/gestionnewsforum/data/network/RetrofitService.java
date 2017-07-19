package com.asifadam93.gestionnewsforum.data.network;

import android.util.Log;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.data.IService;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.local.RealmService;
import com.asifadam93.gestionnewsforum.util.Const;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.Post;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public class RetrofitService implements IService {

    private IRetrofitService service;
    private static RetrofitService retrofitService;

    final Realm realm = Realm.getDefaultInstance();

    public static RetrofitService getInstance() {
        if (retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

    /**
     * Auth
     */

    @Override
    public void login(Map<String, String> loginMap, final IServiceResultListener<String> resultListener) {

        getService().login(loginMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {

                    switch (response.code()) {
                        case 404:
                            result.setErrorMsg("Incorrect login details");
                            break;

                        case 400:
                            result.setErrorMsg("Email or password field is empty");
                            break;

                        default:
                            result.setErrorMsg("Error : Connection");
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

        getService().subscribe(subscribeMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData("Subscription finished, you can sign in");
                } else {
                    result.setErrorMsg("Error : Subscription");
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

        getService().getUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                ServiceResult<User> result = new ServiceResult<User>();

                if (response.isSuccessful()) {
                    result.setData(response.body());
                } else {
                    result.setErrorMsg("Error : Retrieving user data");
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

        getService().updateUser(token, updateMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData("User updated");
                } else {
                    result.setErrorMsg("Error : User update");
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

    /**
     * News
     */

    @Override
    public void createNews(String token, Map<String, String> newsMap, final IServiceResultListener<String> resultListener) {

        getService().createNews(token, newsMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> result = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    result.setData("News added");
                } else {
                    result.setErrorMsg("Error : User update");
                }

                if (resultListener != null) {
                    resultListener.onResult(result);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void getNewsList(String token, final IServiceResultListener<List<News>> result) {

        getService().getNews(token).enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {

                final ServiceResult<List<News>> serviceResult = new ServiceResult<List<News>>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    serviceResult.setErrorMsg("Error on getting news list");
                }

                if (result != null) {
                    //sauvegarde des news récupées en Async (pour ne pas ralentir l'UI)
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (News news : serviceResult.getData()) {
                                Log.i("AJOUT N", news.toString());
                                realm.copyToRealmOrUpdate(news);
                            }
                        }
                    }, new Realm.Transaction.OnSuccess() {

                        @Override
                        public void onSuccess() {
                            Log.i("getNewsList", "Enregistrement des news : OK");

                        }
                    }, new Realm.Transaction.OnError() {

                        @Override
                        public void onError(Throwable error) {
                            Log.e("getNewsList", "Enregistrement des news : KO");
                        }
                    });

                    result.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                if (result != null) {
                    result.onResult(new ServiceResult<List<News>>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void updateNews(final String token, String newsId, Map<String, String> newsMap, final IServiceResultListener<String> resultListener) {

        getService().updateNews(token, newsId, newsMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData("News updated !");
                } else {
                    serviceResult.setErrorMsg("News update error");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }

            }
        });

    }

    @Override
    public void deleteNews(String token, String newsId, final IServiceResultListener<String> resultListener) {

        getService().deleteNews(token, newsId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData("News deleted !");
                } else {
                    serviceResult.setErrorMsg("Error : News delete");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    /**
     * Comment
     */

    @Override
    public void getComments(String token, String url, final IServiceResultListener<List<Comment>> resultListener) {

        getService().getComments(token, url).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                ServiceResult<List<Comment>> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    serviceResult.setErrorMsg("Error : Retrieving comments");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<List<Comment>>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void createComment(String token, Map<String, String> commentMap, final IServiceResultListener<String> resultListener) {

        getService().createComment(token, commentMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Comment created");
                } else {
                    serviceResult.setErrorMsg("Error : Creating comment");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void updateComment(String token, String commentId, Map<String, String> commentMap, final IServiceResultListener<String> resultListener) {

        getService().updateComment(token, commentId, commentMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Comment updated");
                } else {
                    serviceResult.setErrorMsg("Error : Updating comment");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void deleteComment(String token, String commentId, final IServiceResultListener<String> resultListener) {

        getService().deleteComment(token, commentId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Comment deleted");
                } else {
                    serviceResult.setErrorMsg("Error : Deleting comment");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });
    }

    /**
     * Topic
     */

    @Override
    public void getTopicList(String token, final IServiceResultListener<List<Topic>> result) {

        getService().getTopics(token).enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {

                final ServiceResult<List<Topic>> serviceResult = new ServiceResult<List<Topic>>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    serviceResult.setErrorMsg("Error on getting topic list");
                }

                if (result != null) {
                    //sauvegarde des news récupées en Async (pour ne pas ralentir l'UI)
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            for (Topic topic : serviceResult.getData()) {
                                Log.i("AJOUT T", topic.toString());
                                realm.copyToRealmOrUpdate(topic);
                            }
                        }
                    }, new Realm.Transaction.OnSuccess() {

                        @Override
                        public void onSuccess() {
                            Log.i("getTopicList", "Enregistrement des news : OK");

                        }
                    }, new Realm.Transaction.OnError() {

                        @Override
                        public void onError(Throwable error) {
                            Log.e("getTopicList", "Enregistrement des news : OK");
                        }
                    });

                    result.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                if (result != null) {
                    result.onResult(new ServiceResult<List<Topic>>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void createTopic(String token, Map<String, String> topicMap, final IServiceResultListener<String> result) {

        getService().createTopic(token, topicMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Topic created");
                } else {
                    serviceResult.setErrorMsg("Error : Creating topic");
                }

                if (result != null) {
                    result.onResult(serviceResult);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (result != null) {
                    result.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void updateTopic(String token, String topicId, Map<String, String> topicMap, final IServiceResultListener<String> resultListener) {

        getService().updateTopic(token, topicId, topicMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Topic updated");
                } else {
                    serviceResult.setErrorMsg("Error : Updating topic");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void deleteTopic(String token, String topicId, final IServiceResultListener<String> resultListener) {

        getService().deleteTopic(token, topicId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Topic deleted");
                } else {
                    serviceResult.setErrorMsg("Error : Deleting topic");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });
    }

    /**
     * Topic
     */

    @Override
    public void getPost(String token, String postUrl, final IServiceResultListener<List<Post>> resultListener) {

        getService().getPosts(token, postUrl).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                ServiceResult<List<Post>> serviceResult = new ServiceResult<List<Post>>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    serviceResult.setErrorMsg("Retrieving post error");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<List<Post>>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void createPost(String token, Map<String, String> postMap, final IServiceResultListener<String> resultListener) {

        getService().createPost(token, postMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Post created");
                } else {
                    serviceResult.setErrorMsg("Error : Creating post");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });
    }

    @Override
    public void updatePost(String token, String postId, Map<String, String> postMap, final IServiceResultListener<String> resultListener) {

        getService().updatePost(token, postId, postMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Post updated");
                } else {
                    serviceResult.setErrorMsg("Error : Updating post");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });

    }

    @Override
    public void deletePost(String token, String postId, final IServiceResultListener<String> resultListener) {

        getService().deletePost(token, postId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ServiceResult<String> serviceResult = new ServiceResult<String>();

                if (response.isSuccessful()) {
                    serviceResult.setData("Post deleted");
                } else {
                    serviceResult.setErrorMsg("Error : Deleting post");
                }

                if (resultListener != null) {
                    resultListener.onResult(serviceResult);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (resultListener != null) {
                    resultListener.onResult(new ServiceResult<String>(t.getMessage()));
                }
            }
        });
    }


    private IRetrofitService getService() {

        if (service == null) {
            service = RetrofitSession.getInstance().create(IRetrofitService.class);
        }

        return service;
    }


}
