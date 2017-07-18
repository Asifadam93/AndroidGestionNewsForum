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
import com.asifadam93.gestionnewsforum.util.Const;
import com.asifadam93.gestionnewsforum.adapter.TopicAdapter;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;

import java.util.List;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class TopicFragment extends Fragment {

    private RetrofitService retrofitService;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);

        //getTopicList();

        return rootView;
    }

    private void getTopicList() {

        String token = Const.getToken();

        if (token != null) {

            RetrofitService.getInstance().getTopicList(token, new IServiceResultListener<List<Topic>>() {
                @Override
                public void onResult(ServiceResult<List<Topic>> result) {

                    List<Topic> topicList = result.getData();

                    if (topicList != null) {
                        setTopics(topicList);
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

    private void setTopics(List<Topic> topicList) {

        TopicAdapter topicAdapter = new TopicAdapter(getActivity(),topicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(topicAdapter);
    }

}
