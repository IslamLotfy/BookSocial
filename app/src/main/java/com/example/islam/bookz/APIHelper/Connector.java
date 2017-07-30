package com.example.islam.bookz.APIHelper;

/**
 * Created by islam on 29/07/17.
 */


        import android.os.AsyncTask;

import com.example.islam.bookz.Models.GoodreadsResponse;

import rx.Observable;

/**
 * Created by Eslam on 8/12/2016.
 */
public class Connector extends AsyncTask<String, Void, rx.Observable<GoodreadsResponse>> {
    private static final String LOG_TAG=Connector.class.getSimpleName();
    private AsyncDatafetched listener;
    private ApiModule apiModule;
    private BookApiService bookApiService;
    private int type;

    public Connector(){
        apiModule=new ApiModule();
        bookApiService=apiModule.provideApiService();
    }
    @Override
    protected Observable<GoodreadsResponse> doInBackground(String... params) {
        Observable<GoodreadsResponse> response = null;
        switch (type){
            case 1:
                response=bookApiService.getBook(params[0]);
                break;
            case 2:
                response=bookApiService.getAuthorId(params[0]);
                break;
            case 3:
                response=bookApiService.getAuthor(params[0]);
                break;
        }
        return response;
    }
    @Override
    public void onPostExecute(Observable<GoodreadsResponse> movieStr){
        if (listener !=null)
            listener.onDatafetched(movieStr);
    }
    public void setListener(int type, AsyncDatafetched listener){
        this.type=type;
        this.listener=listener;
    }

}
