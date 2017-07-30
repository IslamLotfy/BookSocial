package com.example.islam.bookz.APIHelper;

import com.example.islam.bookz.Models.GoodreadsResponse;

import rx.Observable;

/**
 * Created by islam on 29/07/17.
 */

public interface AsyncDatafetched {
    void onDatafetched(Observable<GoodreadsResponse> response);
}