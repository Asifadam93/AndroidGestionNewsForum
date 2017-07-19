package com.asifadam93.gestionnewsforum.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.activities.NewsActivity;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;

import java.util.ArrayList;
import java.util.List;

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

    class MyViewHoled extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDesc;
        private EditText editTextTitle, editTextContent;
        private AlertDialog dialog;
        List<Comment> commentList = new ArrayList<>();
        LinearLayout linearLayout;

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.row_linear_layout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNewsActivity();
                }
            });
        }

        private void showNewsActivity() {


            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra(NewsActivity.NEWS_CONTENT, newsList.get(getAdapterPosition())); // Parcelable
            context.startActivity(intent);
            /*intent.putExtra(NewsActivity.COMMENT_CONTENT, clickedNews.getContent());
            intent.putExtra(NewsActivity.NEWS_TITLE, clickedNews.getTitle());
            intent.putExtra(NewsActivity.NEWS_ID, clickedNews.getId());*/

        }
    }
}


       /* private void updateNews() {

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
    }
}*/
