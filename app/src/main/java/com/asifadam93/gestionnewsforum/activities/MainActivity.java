package com.asifadam93.gestionnewsforum.activities;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.fragments.NewsFragment;
import com.asifadam93.gestionnewsforum.fragments.TopicFragment;
import com.asifadam93.gestionnewsforum.fragments.UserFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NewsFragment newsFragment;
    private UserFragment userFragment;
    private TopicFragment topicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        addAllFragments();
        showNewsFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.bottom_bar_user:
                Log.i("bottonNav", "bottom_bar_user");
                showUserFragment();
                break;

            case R.id.bottom_bar_news:
                Log.i("bottonNav", "bottom_bar_news");
                showNewsFragment();
                break;

            case R.id.bottom_bar_topic:
                Log.i("bottonNav", "bottom_bar_topic");
                showTopicFragment();
                break;

        }

        return false;
    }

    private void addAllFragments() {
        newsFragment = new NewsFragment();
        userFragment = new UserFragment();
        topicFragment = new TopicFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, newsFragment)
                .add(R.id.fragment_holder, userFragment)
                .add(R.id.fragment_holder, topicFragment)
                .commit();
    }

    private void showNewsFragment() {

        getSupportFragmentManager().beginTransaction()
                .show(newsFragment)
                .hide(userFragment)
                .hide(topicFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showTopicFragment() {

        getSupportFragmentManager().beginTransaction()
                .show(topicFragment)
                .hide(userFragment)
                .hide(newsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showUserFragment() {

        getSupportFragmentManager().beginTransaction()
                .show(userFragment)
                .hide(newsFragment)
                .hide(topicFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        // TODO: 14/07/2017 alertDialog exit
    }
}
