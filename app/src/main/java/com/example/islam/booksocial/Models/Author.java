package com.example.islam.booksocial.Models;

/**
 * Created by islam on 21/07/17.
 */


import com.google.gson.annotations.Expose;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict=false)
public class Author {

    @Element(required=false,name="id")
    @Expose
    private String id;

    @ElementList(required=false,name="books")
    @Expose
    private List<Book> books;


    @Element(required=false,name="name")
    @Expose
    private String name;
    @Element(required=false,name="role")
    @Expose
    private String role;
    @Element(required=false,name="image_url")
    @Expose
    private String imageUrl;
    @Element(required=false,name="small_image_url")
    @Expose
    private String smallImageUrl;
    @Element(required=false,name="link")
    @Expose
    private String link;
    @Element(required=false,name="average_rating")
    @Expose
    private String averageRating;
    @Element(required=false,name="ratings_count")
    @Expose
    private String ratingsCount;
    @Element(required=false,name="text_reviews_count")
    @Expose
    private String textReviewsCount;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(String ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getTextReviewsCount() {
        return textReviewsCount;
    }

    public void setTextReviewsCount(String textReviewsCount) {
        this.textReviewsCount = textReviewsCount;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}