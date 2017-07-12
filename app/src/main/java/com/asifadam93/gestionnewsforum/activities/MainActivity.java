package com.asifadam93.gestionnewsforum.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.asifadam93.gestionnewsforum.R;
import com.asifadam93.gestionnewsforum.fragments.UserFragment;
import com.asifadam93.gestionnewsforum.fragments.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String FRAGMENT_USER = "user";
    private static final String FRAGMENT_NEWS = "news";
    private static final String FRAGMENT_TOPIC = "topic";

    private BottomNavigationView bottomNavigationView;
    private List<UserFragment> fragments = new ArrayList<>(3);

    private NewsFragment newsFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //set news as default position
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
                // TODO: 12/07/2017
                break;

        }

        return false;
    }

    private void showNewsFragment() {

        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        }

        replaceFragment(newsFragment, FRAGMENT_NEWS);
    }

    private void showUserFragment() {

        if (userFragment == null) {
            userFragment = new UserFragment();
        }

        replaceFragment(userFragment, FRAGMENT_USER);
    }


    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, fragment, tag)
                .commit();
    }

}
