package com.asifadam93.gestionnewsforum.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asifadam93 on 16/07/2017.
 */

public class Comment {

    @SerializedName("_id")
    private String id;

    @SerializedName("news")
    private String newsId;

    private String author, title, content;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getNewsId() {
        return newsId;
    }
}