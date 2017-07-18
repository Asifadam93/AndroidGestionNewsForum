package com.asifadam93.gestionnewsforum.network;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.Util.Const;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.model.User;

import java.util.List;
import java.util.Map;

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
                            result.setErrorMsg(Const.getString(R.string.login_incorrect_details));
                            break;

                        case 400:
                            result.setErrorMsg(Const.getString(R.string.login_missing_fields));
                            break;

                        default:
                            result.setErrorMsg(Const.getString(R.string.login_connexion_error));
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
                    result.setData(Const.getString(R.string.inscription));
                } else {
                    result.setErrorMsg(Const.getString(R.string.error_subscribe));
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
                    result.setErrorMsg(Const.getString(R.string.get_user_error));
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
                    result.setData(Const.getString(R.string.user_updated));
                } else {
                    result.setErrorMsg(Const.getString(R.string.error_update_user));
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
                    result.setErrorMsg(Const.getString(R.string.error_update_user));
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

                ServiceResult<List<News>> serviceResult = new ServiceResult<List<News>>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    serviceResult.setErrorMsg(Const.getString(R.string.error_get_news));
                }

                if (result != null) {
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
                    serviceResult.setErrorMsg(Const.getString(R.string.news_update_error));
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
                    serviceResult.setErrorMsg(Const.getString(R.string.error_delete_news));
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
    public void createComment(String token, Map<String, String> commentMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void updateComment(String token, String commentId, Map<String, String> commentMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void deleteComment(String token, String commentId, IServiceResultListener<String> resultListener) {

    }

    /**
     * Topic
     */

    @Override
    public void getTopicList(String token, final IServiceResultListener<List<Topic>> result) {

        getService().getTopics(token).enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {

                ServiceResult<List<Topic>> serviceResult = new ServiceResult<List<Topic>>();

                if (response.isSuccessful()) {
                    serviceResult.setData(response.body());
                } else {
                    serviceResult.setErrorMsg(Const.getString(R.string.error_get_topic));
                }

                if (result != null) {
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
    public void createTopic(String token, Map<String, String> topicMap, IServiceResultListener<String> result) {

    }

    @Override
    public void updateTopic(String token, String topicId, Map<String, String> topicMap, IServiceResultListener<String> resultListener) {

    }

    @Override
    public void deleteTopic(String token, String topicId, IServiceResultListener<String> resultListener) {

    }

    /**
     * Topic
     */

    @Override
    public void getPost(String token, String postUrl, IServiceResultListener<List<Comment>> resultListener) {

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


    private IRetrofitService getService() {

        if (service == null) {
            service = RetrofitSession.getInstance().create(IRetrofitService.class);
        }

        return service;
    }


}
