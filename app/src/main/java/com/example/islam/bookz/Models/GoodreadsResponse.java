package com.example.islam.bookz.Models;

/**
 * Created by islam on 21/07/17.
 */

import com.google.gson.annotations.Expose;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(strict=false)
public class GoodreadsResponse {
    @Element(required=false,name = "Request")
    @Expose
    private Request request;
    @Element(required=false,name = "author")
    @Expose
    private Author author;
    @Element(required=false,name = "book")
    @Expose
    private Book book;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}