package com.example.islam.booksocial.Models;

/**
 * Created by islam on 21/07/17.
 */

import com.google.gson.annotations.Expose;

import org.simpleframework.xml.Element;

public class BookLink {

    @Element(required=false,name="id")
    @Expose
    private String id;
    @Element(required=false,name="name")
    @Expose
    private String name;
    @Element(required=false,name="link")
    @Expose
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
