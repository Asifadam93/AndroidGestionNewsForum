package com.asifadam93.gestionnewsforum.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.adapter.TopicAdapter;
import com.asifadam93.gestionnewsforum.data.IServiceProvider;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.util.Const;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;


import java.util.List;

import io.realm.Realm;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class TopicFragment extends Fragment
{

    private RecyclerView recyclerView;
    final Realm realm = Realm.getDefaultInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);

        getTopicList();

        return rootView;
    }

    private void getTopicList() {

        String token = Const.getToken();

        if (token != null) {

            if (token != null) {

                IServiceProvider.getService(getContext()).getTopicList(token, new IServiceResultListener<List<Topic>>() {
                    @Override
                    public void onResult(ServiceResult<List<Topic>> result) {

                        final List<Topic> topicsList = result.getData();

                        if (topicsList != null) {
                            setTopics(topicsList);
                        } else {
                            Toast.makeText(getActivity(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            } else {
                Toast.makeText(getActivity(), "Token error", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        } else {
            Toast.makeText(getActivity(), "Token error", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

    }

    private void setTopics(List<Topic> topicList) {

        TopicAdapter topicAdapter = new TopicAdapter(getActivity(),topicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(topicAdapter);
    }

}
