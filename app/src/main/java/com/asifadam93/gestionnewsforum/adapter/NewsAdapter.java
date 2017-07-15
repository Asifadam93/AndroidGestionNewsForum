package com.asifadam93.gestionnewsforum.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private Context context;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.newsList = newsList;
    }

    @Override
    public MyViewHoled onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHoled(view, context);
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

        MyViewHoled(View itemView, Context context) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
            textViewTitle.setOnClickListener(this);
            textViewDesc.setOnClickListener(this);
        }

        private void showNewsDialog() {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final View mView = LayoutInflater.from(context).inflate(R.layout.dialog_update_delete, null);

            editTextTitle = (EditText) mView.findViewById(R.id.update_delete_title);
            editTextContent = (EditText) mView.findViewById(R.id.update_delete_content);
            ImageButton buttonComment = (ImageButton) mView.findViewById(R.id.comment_button);
            ImageButton buttonUpdate = (ImageButton) mView.findViewById(R.id.update_button);
            ImageButton buttonDelete = (ImageButton) mView.findViewById(R.id.delete_button);

            editTextTitle.setOnClickListener(this);
            editTextContent.setOnClickListener(this);
            buttonComment.setOnClickListener(this);
            buttonUpdate.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);

            News clickedNews = newsList.get(getAdapterPosition());

            editTextTitle.setText(clickedNews.getTitle());
            editTextContent.setText(clickedNews.getContent());

            builder.setView(mView);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.rv_text_view:
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

                    break;

                case R.id.delete_button:

                    break;

                case R.id.comment_button:

                    break;

            }
        }

        private void updateNews() {



        }

    }
}
