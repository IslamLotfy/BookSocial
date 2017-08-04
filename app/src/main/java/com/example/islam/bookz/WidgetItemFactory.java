package com.example.islam.bookz;

/**
 * Created by islam on 03/08/17.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.islam.bookz.Models.ViewModel;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by islam on 23/04/17.
 */

public class WidgetItemFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private int appWidgetId;
    private List<ViewModel> models=new ArrayList<>();
    private DatabaseReference databaseReference;
    private String userId;



    public WidgetItemFactory(Context context, ArrayList<ViewModel> models) {
        this.context = context;
        this.models=models;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return models.size();
//        Log.e
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
        activityIntent.putExtra(context.getResources().getString(R.string.book_name),models.get(i).getTitle());
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