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
import com.asifadam93.gestionnewsforum.activities.TopicActivity;
import com.asifadam93.gestionnewsforum.util.Const;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.Topic;
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

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHoled> {

    private List<Topic> topicList;
    private LayoutInflater layoutInflater;
    private Context context;

    public TopicAdapter(Context context, List<Topic> topicList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.topicList = topicList;
    }

    @Override
    public MyViewHoled onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHoled(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoled holder, int position) {

        holder.textViewTitle.setText(topicList.get(position).getTitle());
        holder.textViewDesc.setText(topicList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    class MyViewHoled extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTitle, textViewDesc;
        private EditText editTextTitle, editTextContent;
        private AlertDialog dialog;
        List<Comment> commentList = new ArrayList<>();
        //CommentAdapter commentAdapter;

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
            textViewTitle.setOnClickListener(this);
            textViewDesc.setOnClickListener(this);
        }

        private void showTopicActivity() {


            Intent intent = new Intent(context, TopicActivity.class);


            Topic clickedTopic = topicList.get(getAdapterPosition());


            intent.putExtra(TopicActivity.TOPIC_CONTENT, clickedTopic.getContent());
            intent.putExtra(TopicActivity.TOPIC_TITLE, clickedTopic.getTitle());
            intent.putExtra(TopicActivity.TOPIC_ID, clickedTopic.getId());

            context.startActivity(intent);

        }


        @Override
        public void onClick(View view) {
            showTopicActivity();
        }

        private void updateTopic() {

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

                String topicId = topicList.get(getAdapterPosition()).getId();

                RetrofitService.getInstance().updateTopic(token, topicId, updateMap, new IServiceResultListener<String>() {
                    @Override
                    public void onResult(ServiceResult<String> result) {

                        String response = result.getData();

                        if (response != null) {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            refreshTopicList();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        private void deleteTopic() {

            String token = Const.getToken();

            if (token != null) {

                String topicId = topicList.get(getAdapterPosition()).getId();

                RetrofitService.getInstance().deleteTopic(token, topicId, new IServiceResultListener<String>() {
                    @Override
                    public void onResult(ServiceResult<String> result) {

                        String response = result.getData();

                        if (response != null) {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            refreshTopicList();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        }

        private void refreshTopicList() {

            String token = Const.getToken();

            if (token != null) {

                RetrofitService.getInstance().getTopicList(token, new IServiceResultListener<List<Topic>>() {
                    @Override
                    public void onResult(ServiceResult<List<Topic>> result) {

                        List<Topic> topicListTmp = result.getData();

                        if (topicListTmp != null) {
                            topicList.clear();
                            topicList.addAll(topicListTmp);
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
