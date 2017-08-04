package com.example.islam.bookz.Models;

import java.io.Serializable;

/**
 * Created by islam on 31/07/17.
 */

public class ViewModel implements Serializable{
    private String url;
    private String title;

    public ViewModel(){

    }

    public ViewModel(String url, String title) {
        this();
        this.url = url;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
