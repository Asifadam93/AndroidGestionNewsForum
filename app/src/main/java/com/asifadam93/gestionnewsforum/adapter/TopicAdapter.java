package com.asifadam93.gestionnewsforum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.model.Topic;

import java.util.List;

/**
 * Created by Asifadam93 on 15/07/2017.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.MyViewHoled> {

    private List<Topic> topicList;
    private LayoutInflater layoutInflater;

    public TopicAdapter(Context context, List<Topic> newsList) {
        layoutInflater = LayoutInflater.from(context);
        this.topicList = newsList;
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

    class MyViewHoled extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDesc;

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
        }
    }
}