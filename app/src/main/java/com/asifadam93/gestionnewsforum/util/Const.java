package com.asifadam93.gestionnewsforum.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.asifadam93.gestionnewsforum.model.Auth;

import io.realm.Realm;

/**
 * Created by AAD on 11/07/2017.
 */

public class Const {

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static String getToken() {

        Auth auth = Realm.getDefaultInstance().where(Auth.class).findFirst();

        if (auth != null) {
            return auth.getToken();
        }

        return null;
    }

    public static void setToken(final String token) {

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(new Auth("Bearer " + token));
            }
        });
    }

    public static void deleteAuth() {

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Auth.class);
            }
        });

    }

    public static void setUserId(final String userId) {

        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Auth auth = Realm.getDefaultInstance().where(Auth.class).findFirst();
                auth.setUserId(userId);
                realm.copyToRealmOrUpdate(auth);
            }
        });
    }

    public static String getUserId() {

        Auth auth = Realm.getDefaultInstance().where(Auth.class).findFirst();

        if (auth != null) {
            return auth.getUserId();
        }

        return null;
    }

    public static boolean hasPermissionToEdit(String authorId) {

        String userId = getUserId();

        if (userId != null) {

            if (userId.equals(authorId)) {
                return true;
            }
        }
        return false;
    }
}
