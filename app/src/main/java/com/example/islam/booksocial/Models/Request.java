package com.example.islam.booksocial.Models;

/**
 * Created by islam on 21/07/17.
 */

import com.google.gson.annotations.Expose;

import org.simpleframework.xml.Element;

public class Request {

    @Element(required=false,name="authentication")
    @Expose
    private String authentication;
    @Element(required=false,name="key")
    @Expose
    private String key;
    @Element(required=false,name="method")
    @Expose
    private String method;

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
