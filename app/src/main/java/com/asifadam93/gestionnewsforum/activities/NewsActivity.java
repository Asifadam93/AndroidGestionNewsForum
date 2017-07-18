package com.asifadam93.gestionnewsforum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.adapter.CommentAdapter;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.News;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
{
    private CommentAdapter commentAdapter;
    private List<Comment> commentList = new ArrayList<>();

    public static String NEWS_CONTENT = "news_content";
    public static String NEWS_TITLE = "news_title";
    public static String NEWS_ID = "news_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String title    = getIntent().getStringExtra(NEWS_TITLE);
        String content  = getIntent().getStringExtra(NEWS_CONTENT);
        String id       = getIntent().getStringExtra(NEWS_ID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        TextView newsTextView       =  (TextView) findViewById(R.id.news_content);
        FloatingActionButton fab    = (FloatingActionButton) findViewById(R.id.add_comment);
        Toolbar toolbar             = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(title);
        newsTextView.setText(content);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(this, commentList);
        recyclerView.setAdapter(commentAdapter);

        getComments(id);

        Log.i("NewsActivity", "create");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Asif à toi de jouer :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_supprimer) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getComments(String newsId) {

        String token = Const.getToken();

        if (token != null) {

            String url = "/comments?criteria={\"offset\":0,\"where\":{\"news\":\""+newsId+"\"}}";

            Log.i("NewsAdapter","Url : "+url);

            RetrofitService.getInstance().getComments(token, url, new IServiceResultListener<List<Comment>>() {
                @Override
                public void onResult(ServiceResult<List<Comment>> result) {

                    List<Comment> commentListTmp = result.getData();

                    if (commentListTmp != null) {
                        for (Comment comment : commentListTmp) {
                            Log.i("NewsActivity", comment.toString());
                        }
                        commentList.clear();
                        commentList.addAll(commentListTmp);
                        commentAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        Log.i("NewsAdapter",  result.getErrorMsg());
                    }
                }
            });

        }
    }

}
