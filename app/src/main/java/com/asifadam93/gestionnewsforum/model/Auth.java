package com.asifadam93.gestionnewsforum.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Asifadam93 on 18/07/2017.
 */

public class Auth extends RealmObject {

    @PrimaryKey
    private String token;
    private String userId;

    public Auth() {
    }

    public Auth(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
