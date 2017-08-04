package com.example.islam.bookz;

/**
 * Created by islam on 03/08/17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViewsService;

import com.example.islam.bookz.Models.ViewModel;

import java.util.ArrayList;

/**
 * Created by islam on 23/04/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Bundle bundle = intent.getBundleExtra(getResources().getString(R.string.bundle));
        ArrayList<ViewModel> models = (ArrayList<ViewModel>) bundle.getSerializable(getResources().getString(R.string.models));
        return new WidgetItemFactory(getApplicationContext(), models);

    }
}