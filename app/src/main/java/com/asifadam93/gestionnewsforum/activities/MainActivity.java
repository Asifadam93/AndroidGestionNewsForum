package com.asifadam93.gestionnewsforum.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.asifadam93.gestionnewsforum.R;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.bottom_bar_user:
                Log.i("bottonNav","bottom_bar_user");
                break;

            case R.id.bottom_bar_news:
                Log.i("bottonNav","bottom_bar_news");
                break;

            case R.id.bottom_bar_topic:
                Log.i("bottonNav","bottom_bar_topic");
                break;

        }

        return false;
    }
}
