package com.example.islam.bookz;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.islam.bookz.Models.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by islam on 23/04/17.
 */

public class BookzWidget extends AppWidgetProvider {


    public static final String EXTRA_ITEM = "com.ahmedz.socialize.timeline_widget.EXTRA_ITEM";
    private static final String TOUCH_ACTION = "com.ahmedz.socialize.timeline_widget.TOUCH";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(TOUCH_ACTION))
            context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Authenticator authenticator = Authenticator.getInstance();
        List<ViewModel> models=new ArrayList<>();
        for (int appWidgetId : appWidgetIds) {
            Intent activityIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);
            rv.setPendingIntentTemplate(R.id.books_widget_list, pendingIntent);

            RxFireBaseDB.observeSingleValueEvent(FirebaseDatabase.getInstance().getReference().child(context.getResources().getString(R.string.user_node)).child(authenticator.getUserID()).child(context.getResources().getString(R.string.book_node)))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(dataSnapshot -> {
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            ViewModel model=dataSnapshot1.getValue(ViewModel.class);
                            models.add(model);
                        }
                        Intent intent = new Intent(context, WidgetService.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(context.getResources().getString(R.string.models), (Serializable) models);
                        intent.putExtra(context.getResources().getString(R.string.bundle), bundle);

                        rv.setRemoteAdapter(R.id.books_widget_list, intent);
                        rv.setEmptyView(R.id.container, R.id.empty_text_view);

                        appWidgetManager.updateAppWidget(appWidgetId, rv);

                    },throwable -> {
                        Log.e(context.getResources().getString(R.string.error),context.getResources().getString(R.string.error_retrieving_data));
                    });
        }
    }

}
