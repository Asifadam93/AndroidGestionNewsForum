package com.asifadam93.gestionnewsforum.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.Util.Const;
import com.asifadam93.gestionnewsforum.adapter.NewsAdapter;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.network.IServiceResultListener;
import com.asifadam93.gestionnewsforum.network.RetrofitService;

import java.util.List;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class NewsFragment extends Fragment {

    private RetrofitService retrofitService;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);

        getNewsList();

        return rootView;
    }

    private void getNewsList() {

        String token = Const.getPref(Const.TOKEN, getActivity());

        if (token != null) {

            getRetrofitService().getNewsList(token, new IServiceResultListener<List<News>>() {
                @Override
                public void onResult(ServiceResult<List<News>> result) {

                    List<News> newsList = result.getData();

                    if (newsList != null) {
                        setNews(newsList);
                    } else {
                        Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "Token error", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

    }

    private void setNews(List<News> newsList) {

        NewsAdapter newsAdapter = new NewsAdapter(getActivity(), newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsAdapter);
    }

    public RetrofitService getRetrofitService() {

        if (retrofitService == null) {
            retrofitService = new RetrofitService(getActivity());
        }

        return retrofitService;
    }
}
