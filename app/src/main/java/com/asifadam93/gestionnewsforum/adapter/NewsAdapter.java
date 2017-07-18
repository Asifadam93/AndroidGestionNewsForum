package com.asifadam93.gestionnewsforum.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
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

    class MyViewHoled extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDesc;
        private LinearLayout linearLayout;
        private EditText editTextTitle, editTextContent;
        private AlertDialog dialog;
        List<Comment> commentList = new ArrayList<>();
        CommentAdapter commentAdapter;

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.row_linear_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("NewsAdapter","ll clicked");
                }
            });
        }


        /*private void showNewsDialog() {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final View mView = LayoutInflater.from(context).inflate(R.layout.dialog_update_delete, null);

            editTextTitle = (EditText) mView.findViewById(R.id.update_delete_title);
            editTextContent = (EditText) mView.findViewById(R.id.update_delete_content);
            ImageButton buttonUpdate = (ImageButton) mView.findViewById(R.id.update_button);
            ImageButton buttonDelete = (ImageButton) mView.findViewById(R.id.delete_button);

            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.dialog_rview);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            commentAdapter = new CommentAdapter(context, commentList);
            recyclerView.setAdapter(commentAdapter);

            getComments();

            editTextTitle.setOnClickListener(this);
            editTextContent.setOnClickListener(this);
            buttonUpdate.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);

            News clickedNews = newsList.get(getAdapterPosition());

            editTextTitle.setText(clickedNews.getTitle());
            editTextContent.setText(clickedNews.getContent());

            builder.setView(mView);
            dialog = builder.create();
            dialog.show();
        }

        private void getComments() {

            String token = Const.getToken();

            if (token != null) {

                String newsId = newsList.get(getAdapterPosition()).getId();

                String url = "/comments?criteria={\"offset\":0,\"where\":{\"news\":\""+newsId+"\"}}";

                Log.i("NewsAdapter","Url : "+url);

                RetrofitService.getInstance().getComments(token, url, new IServiceResultListener<List<Comment>>() {
                    @Override
                    public void onResult(ServiceResult<List<Comment>> result) {

                        List<Comment> commentListTmp = result.getData();

                        if (commentListTmp != null) {
                            commentList.clear();
                            commentList.addAll(commentListTmp);
                            commentAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                *//*case R.id.rv_text_view:
                    showNewsDialog();
                    break;

                case R.id.rv_tv_desc:
                    showNewsDialog();
                    break;

                case R.id.update_delete_title:
                    editTextTitle.setCursorVisible(true);
                    break;

                case R.id.update_delete_content:
                    editTextContent.setCursorVisible(true);
                    break;

                case R.id.update_button:

                    if (isUserEditPermission()) {
                        updateNews();
                    } else {
                        Toast.makeText(context, R.string.update_access_denied, Toast.LENGTH_SHORT).show();
                    }

                    break;

                case R.id.delete_button:

                    if (isUserEditPermission()) {
                        deleteNews();
                    } else {
                        Toast.makeText(context, R.string.delete_access_denied, Toast.LENGTH_SHORT).show();
                    }

                    break;*//*

            }
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
        }*/

        /*private boolean isUserEditPermission() {
            String selectedNewsUserId = newsList.get(getAdapterPosition()).getAuthor().trim();
            String actualUserId = Const.getPref(Const.USER_ID, context).trim();
            return selectedNewsUserId.equals(actualUserId);
        }*/
    }
}
