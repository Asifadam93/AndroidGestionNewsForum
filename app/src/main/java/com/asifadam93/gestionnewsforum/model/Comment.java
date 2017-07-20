package com.asifadam93.gestionnewsforum.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Asifadam93 on 16/07/2017.
 */

public class Comment implements Parcelable{

    @SerializedName("_id")
    private String id;

    @SerializedName("news")
    private String newsId;

    private String author, title, content;

    protected Comment(Parcel in) {
        id = in.readString();
        newsId = in.readString();
        author = in.readString();
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(newsId);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(content);
    }
}
