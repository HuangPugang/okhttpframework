package com.paul.okhttpframework.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Paul on 15/12/3.
 */
public class MyApp extends Application {

    private static Context mAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        inti();
    }

    private void inti(){
        mAppContext = getApplicationContext();
    }

    public static Context getMyAppContext(){
        return mAppContext;
    }
}
