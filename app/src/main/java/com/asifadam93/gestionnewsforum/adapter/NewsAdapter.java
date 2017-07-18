package com.asifadam93.gestionnewsforum.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.activities.NewsActivity;
import com.asifadam93.gestionnewsforum.util.Const;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asifadam93 on 15/07/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHoled> {

    private List<News> newsList;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.newsList = newsList;
    }

    @Override
    public MyViewHoled onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHoled(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoled holder, int position) {

        holder.textViewTitle.setText(newsList.get(position).getTitle());
        holder.textViewDesc.setText(newsList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class MyViewHoled extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTitle, textViewDesc;
        private EditText editTextTitle, editTextContent;
        private AlertDialog dialog;
        List<Comment> commentList = new ArrayList<>();

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
            textViewTitle.setOnClickListener(this);
            textViewDesc.setOnClickListener(this);
        }

        private void showNewsActivity() {


            Intent intent = new Intent(context, NewsActivity.class);


            News clickedNews = newsList.get(getAdapterPosition());


            intent.putExtra(NewsActivity.NEWS_CONTENT, clickedNews.getContent());
            intent.putExtra(NewsActivity.NEWS_TITLE, clickedNews.getTitle());
            intent.putExtra(NewsActivity.NEWS_ID, clickedNews.getId());

            context.startActivity(intent);
        }


        @Override
        public void onClick(View view) {
            showNewsActivity();
        }

        private void updateNews() {

            String title = editTextTitle.getText().toString();
            String content = editTextContent.getText().toString();

            if (title.isEmpty()) {
                editTextTitle.setError(context.getString(R.string.empty_field));
                return;
            }

            if (content.isEmpty()) {
                editTextTitle.setError(context.getString(R.string.empty_field));
                return;
            }

            Map<String, String> updateMap = new HashMap<>();
            updateMap.put("title", title);
            updateMap.put("content", content);

            String token = Const.getToken();

            if (token != null) {

                String newsId = newsList.get(getAdapterPosition()).getId();

                RetrofitService.getInstance().updateNews(token, newsId, updateMap, new IServiceResultListener<String>() {
                    @Override
                    public void onResult(ServiceResult<String> result) {

                        String response = result.getData();

                        if (response != null) {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            refreshNewsList();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        private void deleteNews() {

            String token = Const.getToken();

            if (token != null) {

                String newsId = newsList.get(getAdapterPosition()).getId();

                RetrofitService.getInstance().deleteNews(token, newsId, new IServiceResultListener<String>() {
                    @Override
                    public void onResult(ServiceResult<String> result) {

                        String response = result.getData();

                        if (response != null) {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            refreshNewsList();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }

        private void refreshNewsList() {

            String token = Const.getToken();

            if (token != null) {

                RetrofitService.getInstance().getNewsList(token, new IServiceResultListener<List<News>>() {
                    @Override
                    public void onResult(ServiceResult<List<News>> result) {

                        List<News> newsListTmp = result.getData();

                        if (newsListTmp != null) {
                            newsList.clear();
                            newsList.addAll(newsListTmp);
                            notifyDataSetChanged();
                            dialog.cancel();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}
