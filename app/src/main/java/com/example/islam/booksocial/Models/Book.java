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
public class Book {

    @Element(required=false,name="id")
    @Expose
    private String id;
    @Element(required=false,name="title")
    @Expose
    private String title;
    @Element(required=false,name="isbn")
    @Expose
    private String isbn;
    @Element(required=false,name="isbn13")
    @Expose
    private String isbn13;
    @Element(required=false,name="asin")
    @Expose
    private String asin;
    @Element(required=false,name="kindle_asin")
    @Expose
    private String kindleAsin;
    @Element(required=false,name="marketplace_id")
    @Expose
    private String marketplaceId;
    @Element(required=false,name="country_code")
    @Expose
    private String countryCode;
    @Element(required=false,name="image_url")
    @Expose
    private String imageUrl;
    @Element(required=false,name="small_image_url")
    @Expose
    private String smallImageUrl;
    @Element(required=false,name="publication_year")
    @Expose
    private String publicationYear;
    @Element(required=false,name="publication_month")
    @Expose
    private String publicationMonth;
    @Element(required=false,name="publication_day")
    @Expose
    private String publicationDay;
    @Element(required=false,name="publisher")
    @Expose
    private String publisher;
    @Element(required=false,name="language_code")
    @Expose
    private String languageCode;
    @Element(required=false,name="is_ebook")
    @Expose
    private String isEbook;
    @Element(required=false,name="description")
    @Expose
    private String description;
//    @Element(required=false,name="work")
//    @Expose
//    private Work work;
    @Element(required=false,name="average_rating")
    @Expose
    private String averageRating;
    @Element(required=false,name="num_pages")
    @Expose
    private String numPages;
    @Element(required=false,name="format")
    @Expose
    private String format;
    @Element(required=false,name="edition_information")
    @Expose
    private String editionInformation;
    @Element(required=false,name="ratings_count")
    @Expose
    private String ratingsCount;
    @Element(required=false,name="text_reviews_count")
    @Expose
    private String textReviewsCount;
    @Element(required=false,name="url")
    @Expose
    private String url;
    @Element(required=false,name="link")
    @Expose
    private String link;
    @ElementList(required=false,name="authors")
    @Expose
    private List<Author> authors;
//    @Element(required=false,name="reviews_widget")
//    @Expose
//    private ReviewsWidget reviewsWidget;
//    @Element(required=false,name="popular_shelves")
//    @Expose
//    private PopularShelves popularShelves;
    @ElementList(required=false,name="book_links")
    @Expose
    private List<BookLink> bookLinks;
    @ElementList(required=false,name="buy_links")
    @Expose
    private List<BuyLink> buyLinks;
    @Element(required=false,name="series_works")
    @Expose
    private String seriesWorks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getKindleAsin() {
        return kindleAsin;
    }

    public void setKindleAsin(String kindleAsin) {
        this.kindleAsin = kindleAsin;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublicationMonth() {
        return publicationMonth;
    }

    public void setPublicationMonth(String publicationMonth) {
        this.publicationMonth = publicationMonth;
    }

    public String getPublicationDay() {
        return publicationDay;
    }

    public void setPublicationDay(String publicationDay) {
        this.publicationDay = publicationDay;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(String isEbook) {
        this.isEbook = isEbook;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//
//    public Work getWork() {
//        return work;
//    }
//
//    public void setWork(Work work) {
//        this.work = work;
//    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getNumPages() {
        return numPages;
    }

    public void setNumPages(String numPages) {
        this.numPages = numPages;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEditionInformation() {
        return editionInformation;
    }

    public void setEditionInformation(String editionInformation) {
        this.editionInformation = editionInformation;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

//    public ReviewsWidget getReviewsWidget() {
//        return reviewsWidget;
//    }
//
//    public void setReviewsWidget(ReviewsWidget reviewsWidget) {
//        this.reviewsWidget = reviewsWidget;
//    }
//
//    public PopularShelves getPopularShelves() {
//        return popularShelves;
//    }
//
//    public void setPopularShelves(PopularShelves popularShelves) {
//        this.popularShelves = popularShelves;
//    }

    public List<BookLink> getBookLinks() {
        return bookLinks;
    }

    public void setBookLinks(List<BookLink> bookLinks) {
        this.bookLinks = bookLinks;
    }

    public List<BuyLink> getBuyLinks() {
        return buyLinks;
    }

    public void setBuyLinks(List<BuyLink> buyLinks) {
        this.buyLinks = buyLinks;
    }

    public String getSeriesWorks() {
        return seriesWorks;
    }

    public void setSeriesWorks(String seriesWorks) {
        this.seriesWorks = seriesWorks;
    }

}