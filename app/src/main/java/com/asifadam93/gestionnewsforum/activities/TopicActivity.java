package com.asifadam93.gestionnewsforum.activities;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.adapter.CommentAdapter;
import com.asifadam93.gestionnewsforum.adapter.PostAdapter;
import com.asifadam93.gestionnewsforum.data.IServiceProvider;
import com.asifadam93.gestionnewsforum.data.IServiceResultListener;
import com.asifadam93.gestionnewsforum.data.network.RetrofitService;
import com.asifadam93.gestionnewsforum.model.Comment;
import com.asifadam93.gestionnewsforum.model.Post;
import com.asifadam93.gestionnewsforum.model.ServiceResult;
import com.asifadam93.gestionnewsforum.model.Topic;
import com.asifadam93.gestionnewsforum.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicActivity extends AppCompatActivity {

    private PostAdapter postAdapter;
    private List<Post> postsList = new ArrayList<>();

    public static String TOPIC_CONTENT = "topic_content";
    private Topic clickedTopic;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        clickedTopic = getIntent().getParcelableExtra(TOPIC_CONTENT);

        if (clickedTopic == null) {
            finish();
            Toast.makeText(this, R.string.error_data, Toast.LENGTH_SHORT).show();
        }


        TextView newsTextView       =  (TextView) findViewById(R.id.news_content);
        FloatingActionButton fab    = (FloatingActionButton) findViewById(R.id.add_comment);
        Toolbar toolbar             = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(clickedTopic.getTitle());
        newsTextView.setText(clickedTopic.getContent());


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(this, postsList);
        recyclerView.setAdapter(postAdapter);

        getPosts();

        Log.i("NewsActivity", "create");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Asif à toi de jouer :)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                showAddCommentDialog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPosts();
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


    private void getPosts() {

        String token = Const.getToken();

        if (token != null) {

            String url = "/posts?criteria={\"offset\":0,\"where\":{\"topic\":\""+clickedTopic.getId()+"\"}}";

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

    private void showAddCommentDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_add, null);

        TextView textViewTitle = (TextView) mView.findViewById(R.id.add_welcome_title);
        final EditText editTextTitle = (EditText) mView.findViewById(R.id.add_title);
        final EditText editTextContent = (EditText) mView.findViewById(R.id.add_content);
        Button buttonSave = (Button) mView.findViewById(R.id.module_button_save);

        //change welcome title
        textViewTitle.setText(R.string.add_post);

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
                addCommentMap.put("topic", clickedTopic.getId());
                addPost(addCommentMap);
            }
        });
    }

    private void addPost(Map<String, String> map) {

        String token = Const.getToken();

        if (token != null) {

            IServiceProvider.getService(this).createPost(token, map, new IServiceResultListener<String>() {
                @Override
                public void onResult(ServiceResult<String> result) {

                    String res = result.getData();

                    if (res != null) {
                        Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
                        getPosts();
                        dialog.cancel();
                    } else {
                        Toast.makeText(getBaseContext(), result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
