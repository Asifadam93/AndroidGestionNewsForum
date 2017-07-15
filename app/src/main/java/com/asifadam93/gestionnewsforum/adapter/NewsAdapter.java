package com.asifadam93.gestionnewsforum.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.model.News;

import java.util.List;

/**
 * Created by Asifadam93 on 15/07/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHoled> {

    private List<News> newsList;
    private LayoutInflater layoutInflater;

    public NewsAdapter(Context context, List<News> newsList) {
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

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
        }
    }
}
