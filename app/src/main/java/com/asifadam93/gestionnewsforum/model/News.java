package com.asifadam93.gestionnewsforum.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Asifadam93 on 15/07/2017.
 */

public class News extends RealmObject implements Parcelable
{
    @SerializedName("_id")
    @PrimaryKey
    private String id;

    private String author;
    private String title;
    private String content;
    private String date;
    private User realUser;

    protected News(Parcel in) {
        id = in.readString();
        author = in.readString();
        title = in.readString();
        content = in.readString();
        date = in.readString();
    }

    public News(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public News() {
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
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
    public String getDate() {
        return date;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(date);
    }

    public User getRealUser() {
        return realUser;
    }

    public void setRealUser(User realUser) {
        this.realUser = realUser;
    }
}
