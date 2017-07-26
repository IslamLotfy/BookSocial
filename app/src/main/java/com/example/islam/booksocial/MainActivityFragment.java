package com.example.islam.booksocial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.islam.booksocial.APIHelper.ApiModule;
import com.example.islam.booksocial.APIHelper.BookApiService;
import com.example.islam.booksocial.Models.GoodreadsResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ApiModule apiModule;
    private BookApiService bookApiService;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         apiModule=new ApiModule();
         bookApiService=apiModule.provideApiService();
        rx.Observable<GoodreadsResponse> book= bookApiService.getBook("ثلاثية غرناطة");
        book.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e("moviesccc",result.getBook().getTitle());
                },throwable -> {
                    Log.e("errorrrrr",throwable.toString());
                });

        rx.Observable<GoodreadsResponse> author= bookApiService.getAuthorId("Radwa Ashour");
        author.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    String id=getID(result.getAuthor().getLink());
                    Log.e("movies",id);
                    getAuthor(id);
                },throwable -> {
                    Log.e("errorrrrr",throwable.toString());
                });

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void getAuthor(String id) {
        rx.Observable<GoodreadsResponse> author= bookApiService.getAuthor(id);
        author.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Log.e("movies",result.getAuthor().getBooks().get(0).getTitle());
                },throwable -> {
                    Log.e("errorrrrr",throwable.toString());
                });

    }

    public String getID(String link){
        String id="";
        int x=link.indexOf("show/");
        for (int i=x+5 ; i< link.length() ; i++){
            if(link.charAt(i)!='.'){
                id+=link.charAt(i);
            }else{
                break;
            }
        }
        return id;
    }
}
