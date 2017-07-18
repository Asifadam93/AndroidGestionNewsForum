package com.asifadam93.gestionnewsforum.activities;

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
import com.asifadam93.gestionnewsforum.adapter.PostAdapter;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.Post;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity {

    private PostAdapter postAdapter;
    private List<Post> postsList = new ArrayList<>();

    public static String TOPIC_CONTENT = "news_content";
    public static String TOPIC_TITLE = "news_title";
    public static String TOPIC_ID = "news_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String title    = getIntent().getStringExtra(TOPIC_TITLE);
        String content  = getIntent().getStringExtra(TOPIC_CONTENT);
        String id       = getIntent().getStringExtra(TOPIC_ID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        TextView newsTextView       =  (TextView) findViewById(R.id.news_content);
        FloatingActionButton fab    = (FloatingActionButton) findViewById(R.id.add_comment);
        Toolbar toolbar             = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(title);
        newsTextView.setText(content);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this, postsList);
        recyclerView.setAdapter(postAdapter);

        getPosts(id);

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


    private void getPosts(String postId) {

        String token = Const.getToken();

        if (token != null) {

            String url = "/posts?criteria={\"offset\":0,\"where\":{\"topic\":\""+postId+"\"}}";

            Log.i("NewsAdapter","Url : "+url);

            RetrofitService.getInstance().getPost(token, url, new IServiceResultListener<List<Post>>() {
                @Override
                public void onResult(ServiceResult<List<Post>> result) {

                    List<Post> postsListTmp = result.getData();

                    if (postsListTmp != null) {
                        for (Post comment : postsListTmp) {
                            Log.i("NewsActivity", comment.toString());
                        }
                        postsList.clear();
                        postsList.addAll(postsListTmp);
                        postAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        Log.i("NewsAdapter",  result.getErrorMsg());
                    }
                }
            });

        }
    }
}
