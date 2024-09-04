package com.dicoding.librarymanagementapp.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Book implements Parcelable {
    private int id;
    private String name;
    private String author;
    private String publisher;
    private String category;

    private Book(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.author = in.readString();
        this.publisher = in.readString();
        this.category = in.readString();
    }

    public Book() {
    }

    public Book(int id, String name, String author, String publisher, String category) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(category);
    }
}
