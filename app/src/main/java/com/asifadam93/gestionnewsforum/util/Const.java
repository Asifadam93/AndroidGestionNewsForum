package com.asifadam93.gestionnewsforum.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by AAD on 11/07/2017.
 */

public class Const {

    public final static String TOKEN = "TOKEN";
    public final static String USER_ID = "USER_ID";
    public final static String SHARED_PREF_NAME = "UserPref";

    public static void putPref(String key, String value, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Const.SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPref(String key, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Const.SHARED_PREF_NAME, 0);
        return prefs.getString(key, null);
    }

    public static boolean isInternetAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }
}
