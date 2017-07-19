package com.asifadam93.gestionnewsforum.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.activities.MainActivity;
import com.asifadam93.gestionnewsforum.adapter.NewsAdapter;
import com.asifadam93.gestionnewsforum.data.IService;
import com.asifadam93.gestionnewsforum.data.IServiceProvider;
import com.asifadam93.gestionnewsforum.data.local.RealmService;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.User;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class NewsFragment extends Fragment {

    private RetrofitService retrofitService;
    private RecyclerView recyclerView;
    private AlertDialog dialog;
    public NewsAdapter newsAdapter;
    public List<News> news = new ArrayList<>();
    final Realm realm = Realm.getDefaultInstance();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsAdapter = new NewsAdapter(getActivity(), getNews());
        recyclerView.setAdapter(newsAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Const.isInternetAvailable(getContext())) {
                    showAddDialog();
                } else {
                    Toast.makeText(getContext(), getString(R.string.internet_not_available), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getNewsList();
    }

    private void getNewsList() {

        String token = Const.getToken();

        if (token != null) {

            IServiceProvider.getService(getContext()).getNewsList(token, new IServiceResultListener<List<News>>() {
                @Override
                public void onResult(ServiceResult<List<News>> result) {

                    final List<News> newsList = result.getData();

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

    private void showAddDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_add, null);

        final EditText editTextTitle = (EditText) mView.findViewById(R.id.add_title);
        final EditText editTextContent = (EditText) mView.findViewById(R.id.add_content);
        Button buttonSave = (Button) mView.findViewById(R.id.module_button_save);

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

                addNews(addNewsMap);
            }
        });
    }

    private void addNews(Map<String, String> map) {

        String token = Const.getToken();

        if (token != null) {

            RetrofitService.getInstance().createNews(token, map, new IServiceResultListener<String>() {
                @Override
                public void onResult(ServiceResult<String> result) {

                    String response = result.getData();

                    if(response != null){
                        Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                        getNewsList();
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


    public List<News> getNews() {

        return news;
    }

    public void setNews(List<News> news) {
        this.news.clear();
        this.news.addAll(news);
        updateAdapter();
    }

    public void updateAdapter() {
        if (!MainActivity.utilisateurs.isEmpty()) {

            for (News aNews : newsAdapter.newsList ) {
                Log.i("News User ", MainActivity.utilisateurs.get(aNews.getAuthor()).toString());
                aNews.setRealUser(MainActivity.utilisateurs.get(aNews.getAuthor()));
            }

        }
        newsAdapter.notifyDataSetChanged();
    }



}
