package com.wx.multihero;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    
    private static Context context;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
    }
    
    public static Context getContext() {
        return context;
    }
}
