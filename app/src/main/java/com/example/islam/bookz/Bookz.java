package com.example.islam.bookz;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by islam on 29/07/17.
 */

public class Bookz extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
//        if(!FirebaseApp.getApps(this).isEmpty()){
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//        }
    }
}
