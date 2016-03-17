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
        String s = "test";
        registerReceiver(mHomeKeyEventReceiver,new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
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


    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台
                    Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };
}
