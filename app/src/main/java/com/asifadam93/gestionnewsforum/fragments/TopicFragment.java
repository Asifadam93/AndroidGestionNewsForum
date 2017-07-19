package com.asifadam93.gestionnewsforum.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.adapter.TopicAdapter;
import com.asifadam93.gestionnewsforum.data.IServiceProvider;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class TopicFragment extends Fragment {

    private RecyclerView recyclerView;
    private Dialog dialog;
    TopicAdapter topicAdapter;
    List<Topic> topicList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        topicList = new ArrayList<>();
        topicAdapter = new TopicAdapter(getActivity(), topicList);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(topicAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getTopicList();
    }

    private void showAddDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null);

        TextView textView = (TextView)mView.findViewById(R.id.add_welcome_title);
        final EditText editTextTitle = (EditText) mView.findViewById(R.id.add_title);
        final EditText editTextContent = (EditText) mView.findViewById(R.id.add_content);
        Button buttonSave = (Button) mView.findViewById(R.id.module_button_save);

        textView.setText(R.string.add_topic);

        builder.setView(mView);
        dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();

                if (title.isEmpty()) {
                    editTextTitle.setError(getString(R.string.empty_field));
                    return;
                }

                if (content.isEmpty()) {
                    editTextContent.setError(getString(R.string.empty_field));
                    return;
                }

                Map<String, String> addNewsMap = new HashMap<>();
                addNewsMap.put("title", title);
                addNewsMap.put("content", content);

                addTopic(addNewsMap);
            }
        });
    }

    private void addTopic(Map<String, String> map) {

        String token = Const.getToken();

        if (token != null) {

            RetrofitService.getInstance().createTopic(token, map, new IServiceResultListener<String>() {
                @Override
                public void onResult(ServiceResult<String> result) {

                    String response = result.getData();

                    if(response != null){
                        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                        getTopicList();
                        dialog.cancel();
                    } else {
                        Toast.makeText(getActivity(),result.getErrorMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            Toast.makeText(getActivity(), "Token error", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }

    private void getTopicList() {

        String token = Const.getToken();

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

    }

    private void setTopics(List<Topic> topicListTmp) {
        topicList.clear();
        topicList.addAll(topicListTmp);
        topicAdapter.notifyDataSetChanged();
    }

}
