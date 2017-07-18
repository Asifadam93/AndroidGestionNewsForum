package com.asifadam93.gestionnewsforum.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Asifadam93 on 15/07/2017.
 */

public class News extends RealmObject
{
    @SerializedName("_id")
    @PrimaryKey
    private String id;

    private String author;
    private String title;
    private String content;
    private String date;

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
    public String getDate() {
        return date;
    }

    public News(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public News() {
    }


    //TODO récupérer la date ! ! !

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
