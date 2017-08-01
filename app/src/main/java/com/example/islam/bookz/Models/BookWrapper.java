package com.example.islam.bookz.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by islam on 01/08/17.
 */

public class BookWrapper implements Parcelable {
    private Book book;

    public BookWrapper(Book book) {
        this.book = book;
    }
    public BookWrapper(){

    }
    protected BookWrapper(Parcel in) {
    }

    public static final Creator<BookWrapper> CREATOR = new Creator<BookWrapper>() {
        @Override
        public BookWrapper createFromParcel(Parcel in) {
            return new BookWrapper(in);
        }

        @Override
        public BookWrapper[] newArray(int size) {
            return new BookWrapper[size];
        }
    };

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{String.valueOf(this.book)});
    }
}
