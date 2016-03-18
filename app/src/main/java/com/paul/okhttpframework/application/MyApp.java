package com.paul.okhttpframework.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Paul on 15/12/3.
 */
public class MyApp extends Application {

    private static Context mAppContext;
    private static MyApp myApp;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        init();
    }

    private void init(){
        mAppContext = getApplicationContext();
    }

    public static Context getMyAppContext(){
        return mAppContext;
    }

    public static MyApp getApp(){
        return myApp;
    }
}
