package com.asifadam93.gestionnewsforum.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.asifadam93.gestionnewsforum.R;

public class NewsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    public static String NEWS_CONTENT = "url_article";
    public static String NEWS_TITLE = "url_titre";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String title = getIntent().getStringExtra(NEWS_TITLE);
        String content = getIntent().getStringExtra(NEWS_CONTENT);

        Log.i("NewsActivity", "create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        TextView newsTextView =  (TextView) findViewById(R.id.news_content);

        Log.i("NewsActivity", "create");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        newsTextView.setText(content);

        Log.i("NewsActivity", "create");

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
}
