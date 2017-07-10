package com.asifadam93.gestionnewsforum.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public class User {

    @SerializedName("_id")
    private Integer id;
    private String email, firstName, lastName;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
