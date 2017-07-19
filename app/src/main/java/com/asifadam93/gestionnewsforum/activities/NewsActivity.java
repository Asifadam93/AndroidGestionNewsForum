package com.asifadam93.gestionnewsforum.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.adapter.CommentAdapter;
import com.asifadam93.gestionnewsforum.data.IServiceProvider;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsActivity extends AppCompatActivity {
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();

    public static String NEWS_CONTENT = "news_content";
    public static String NEWS_TITLE = "news_title";
    public static String NEWS_ID = "news_id";

    private Dialog dialog;
    private News clickededNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*String title = getIntent().getStringExtra(NEWS_TITLE);
        String content = getIntent().getStringExtra(NEWS_CONTENT);
        newsId = getIntent().getStringExtra(NEWS_ID);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        clickededNews = getIntent().getParcelableExtra(NEWS_CONTENT);

        if(clickededNews == null){
            finish();
            Toast.makeText(this, R.string.error_data,Toast.LENGTH_SHORT).show();
        }

        TextView newsTextView = (TextView) findViewById(R.id.news_content);
        FloatingActionButton fabAddComment = (FloatingActionButton) findViewById(R.id.add_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(clickededNews.getTitle());
        newsTextView.setText(clickededNews.getContent());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this, commentList);
        recyclerView.setAdapter(commentAdapter);

        getComments();

        Log.i("NewsActivity", "create");


        fabAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCommentDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_modifier) {

            if(Const.hasPermissionToEdit(clickededNews.getAuthor()))

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_supprimer) {



            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getComments() {

        String token = Const.getToken();

        if (token != null) {

            String url = "/comments?criteria={\"offset\":0,\"where\":{\"news\":\"" + clickededNews.getId() + "\"}}";

            Log.i("NewsAdapter", "Url : " + url);

            RetrofitService.getInstance().getComments(token, url, new IServiceResultListener<List<Comment>>() {
                @Override
                public void onResult(ServiceResult<List<Comment>> result) {

                    List<Comment> commentListTmp = result.getData();

                    if (commentListTmp != null) {
                        /*for (Comment comment : commentListTmp) {
                            Log.i("NewsActivity", comment.toString());
                        }*/
                        commentList.clear();
                        commentList.addAll(commentListTmp);
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        Log.i("NewsAdapter", result.getErrorMsg());
                    }
                }
            });
        }
    }

    private void showAddCommentDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);

        TextView textViewTitle = (TextView) mView.findViewById(R.id.add_welcome_title);
        final EditText editTextTitle = (EditText) mView.findViewById(R.id.add_title);
        final EditText editTextContent = (EditText) mView.findViewById(R.id.add_content);
        Button buttonSave = (Button) mView.findViewById(R.id.module_button_save);

        //change welcome title
        textViewTitle.setText(R.string.add_comment);

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

                Map<String, String> addCommentMap = new HashMap<>();
                addCommentMap.put("title", title);
                addCommentMap.put("content", content);
                addCommentMap.put("news",clickededNews.getId());
                addComment(addCommentMap);
            }
        });
    }

    private void addComment(Map<String, String> map) {

        String token = Const.getToken();

        if (token != null) {

            IServiceProvider.getService(this).createComment(token, map, new IServiceResultListener<String>() {
                @Override
                public void onResult(ServiceResult<String> result) {

                    String res = result.getData();

                    if (res != null) {
                        Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
                        getComments();
                        dialog.cancel();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void deleteNews() {

        String token = Const.getToken();

        if (token != null) {

            RetrofitService.getInstance().deleteNews(token, clickededNews.getId(), new IServiceResultListener<String>() {
                @Override
                public void onResult(ServiceResult<String> result) {

                    String response = result.getData();

                    if (response != null) {
                        Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
                        //refreshNewsList();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
