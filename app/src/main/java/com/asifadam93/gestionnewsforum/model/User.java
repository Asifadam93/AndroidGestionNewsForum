package com.asifadam93.gestionnewsforum.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public class User {

    @SerializedName("_id")
    private String id;
    private String email;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;

    public String getId() {
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
