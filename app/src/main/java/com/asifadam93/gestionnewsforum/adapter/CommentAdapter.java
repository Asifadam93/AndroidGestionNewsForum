package com.asifadam93.gestionnewsforum.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.data.IServiceProvider;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asifadam93 on 15/07/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHoled> {

    private List<Comment> commentList;
    private LayoutInflater layoutInflater;
    private Context context;

    public CommentAdapter(Context context, List<Comment> newsList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.commentList = newsList;
    }

    @Override
    public MyViewHoled onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHoled(view);
    }

    @Override
    public void onBindViewHolder(MyViewHoled holder, int position) {

        holder.textViewTitle.setText(commentList.get(position).getTitle());
        holder.textViewDesc.setText(commentList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class MyViewHoled extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDesc;
        private LinearLayout linearLayout;
        private Dialog dialog;
        private Comment clickedComment;

        MyViewHoled(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.rv_text_view);
            textViewDesc = (TextView) itemView.findViewById(R.id.rv_tv_desc);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.row_linear_layout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    clickedComment = commentList.get(getAdapterPosition());

                    if (Const.hasPermissionToEdit(clickedComment.getAuthor())) {
                        showCommentDialog();
                    } else {
                        Toast.makeText(context, context.getString(R.string.edit_access_denied), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void showCommentDialog() {

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final View mView = LayoutInflater.from(context).inflate(R.layout.dialog_update_delete, null);

            TextView textViewTitle = (TextView) mView.findViewById(R.id.add_welcome_title);
            final EditText editTextTitle = (EditText) mView.findViewById(R.id.add_title);
            final EditText editTextContent = (EditText) mView.findViewById(R.id.add_content);
            Button buttonSave = (Button) mView.findViewById(R.id.module_button_save);
            Button buttonDelete = (Button) mView.findViewById(R.id.module_button_delete);

            //change welcome title
            textViewTitle.setText(R.string.welcome_edit_comment);
            editTextTitle.setText(clickedComment.getTitle());
            editTextContent.setText(clickedComment.getContent());

            builder.setView(mView);
            dialog = builder.create();
            dialog.show();

            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Map<String, String> updateCommentMap = new HashMap<>();
                    updateCommentMap.put("title", editTextTitle.getText().toString());
                    updateCommentMap.put("content", editTextContent.getText().toString());

                    updateComment(updateCommentMap);
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteComment();
                }
            });
        }

        private void updateComment(Map<String, String> commentMap) {

            String token = Const.getToken();

            if (token != null) {

                Log.i("CommentAdapter", "deleteComment");

                IServiceProvider.getService(context).updateComment(token, clickedComment.getId(),
                        commentMap, new IServiceResultListener<String>() {
                            @Override
                            public void onResult(ServiceResult<String> result) {

                                String response = result.getData();

                                if (response != null) {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    getComments();
                                    dialog.cancel();
                                } else {
                                    Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

        private void deleteComment() {

            String token = Const.getToken();

            if (token != null) {

                Log.i("CommentAdapter", "deleteComment");

                IServiceProvider.getService(context).deleteComment(token, clickedComment.getId(), new IServiceResultListener<String>() {
                    @Override
                    public void onResult(ServiceResult<String> result) {

                        String response = result.getData();

                        if (response != null) {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            getComments();
                            dialog.cancel();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        private void getComments() {

            String token = Const.getToken();

            if (token != null) {

                String url = "/comments?criteria={\"offset\":0,\"where\":{\"news\":\"" + clickedComment.getNewsId() + "\"}}";

                Log.i("CommentAdapter", "Url : " + url);

                RetrofitService.getInstance().getComments(token, url, new IServiceResultListener<List<Comment>>() {
                    @Override
                    public void onResult(ServiceResult<List<Comment>> result) {

                        List<Comment> commentListTmp = result.getData();

                        if (commentListTmp != null) {

                            for (Comment comment : commentListTmp) {
                                Log.i("CommentAdapter", comment.toString());
                            }

                            commentList.clear();
                            commentList.addAll(commentListTmp);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                            Log.i("CommentAdapter", result.getErrorMsg());
                        }
                    }
                });
            }
        }
    }
}
