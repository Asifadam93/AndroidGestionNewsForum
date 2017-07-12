package com.asifadam93.gestionnewsforum.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asifadam93.gestionnewsforum.R;


/**
 * Created by Asifadam93 on 12/07/2017.
 */

public class NewsFragment extends Fragment {

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bottom_nav, container, false);

        textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText("News");

        /*String title = getArguments().getString(ARG_TITLE, "");
        textView.setText(title);*/
        return rootView;
    }
}
