package com.example.islam.bookz;

/**
 * Created by islam on 03/08/17.
 */

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.islam.bookz.Models.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by islam on 23/04/17.
 */

public class WidgetItemFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private int appWidgetId;
    private List<ViewModel> models=new LinkedList<>();
    private DatabaseReference databaseReference;
    private String userId;



    public WidgetItemFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        userId= Authenticator.getInstance().getUserID();
    }


    @Override
    public void onCreate() {
        updateDate();

    }

    @Override
    public void onDataSetChanged() {
        updateDate();
    }


    private void updateDate(){
        RxFireBaseDB.observeSingleValueEvent(databaseReference.child("Users").child(userId).child("books"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataSnapshot -> {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        ViewModel model=dataSnapshot1.getValue(ViewModel.class);
                        models.add(model);
                    }
                },throwable -> {
                    Log.e("error","an error occured, please try again");
                });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.book_item_view);
        remoteViews.setTextViewText(R.id.item_view_title,models.get(i).getTitle());
        try {
            Bitmap b = Picasso.with(context).load(models.get(i).getUrl()).get();
            remoteViews.setImageViewBitmap(R.id.item_image_view, b);
        } catch (IOException e) {
            e.printStackTrace();
        }


        final Intent activityIntent = new Intent();
        activityIntent.putExtra("boonName",models.get(i).getTitle());
        remoteViews.setOnClickFillInIntent(R.id.book_list_item, activityIntent);
        return remoteViews;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}