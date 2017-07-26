package com.example.islam.booksocial.APIHelper;

import com.example.islam.booksocial.Models.GoodreadsResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by islam on 21/07/17.
 */

public interface BookApiService {
    @GET("book/title.xml?&")
    Observable<GoodreadsResponse> getBook(@Query("title") String title);

    @GET("author/list/{id}/?format=xml&")
    Observable<GoodreadsResponse> getAuthor(@Path("id") String id);

    @GET("api/author_url/{name}?")
    Observable<GoodreadsResponse> getAuthorId(@Path("name") String name);
}
//https://www.goodreads.com/book/title.xml&title=Hound%20of%20the%20Baskervilles?key=GXB8ewu4haNBZndMmYpng
//https://www.goodreads.com/book/title.xml?&key=GXB8ewu4haNBZndMmYpng&title=Hound%20of%20the%20Baskervilles
//https://www.goodreads.com/book/title.xml/the%20hound%20of%20the%20Baskervilles?key=GXB8ewu4haNBZndMmYpng